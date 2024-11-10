package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.*;

public class SystemConfig {
	private AnchorPane rootPane;
	private TableView<Transaction> dataTable;
	private ObservableList<Transaction> transactions;
	
	public SystemConfig() {
		initializeUI();
	}
	
	public AnchorPane getRoot() {
		return rootPane;
	}
	
	private void initializeUI() {
		rootPane = new AnchorPane();
		
		// Title label 
		Label titleLabel = new Label("System Config - Admin");
		AnchorPane.setTopAnchor(titleLabel, 10.0);
		AnchorPane.setLeftAnchor(titleLabel, 10.0);
		
		// Initialize the table 
		dataTable = new TableView<>();
		dataTable.setEditable(true);
		initializeTableColumns();
		
		// load transactions from database
		transactions = FXCollections.observableArrayList(DatabaseHelper.loadTransactions());
		dataTable.setItems(transactions);
		
		VBox tableBox = new VBox(10, dataTable);
		AnchorPane.setTopAnchor(tableBox, 50.0);
		AnchorPane.setLeftAnchor(tableBox, 10.0);
		AnchorPane.setRightAnchor(tableBox, 10.0);
		
		rootPane.getChildren().addAll(titleLabel, tableBox);
		
	}
	
	private void initializeTableColumns() {
		
		// seller name column 
		TableColumn<Transaction, String> sellerColumn = new TableColumn<>("Seller Name");
		sellerColumn.setCellValueFactory(cellData->cellData.getValue().sellerNameProperty());
		sellerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		sellerColumn.setOnEditCommit(event->{
			Transaction transaction = event.getRowValue();
			transaction.setSellerName(event.getNewValue());
			updateTransactionInDatabase(transaction);
		});
		
		// buyer column 
		
		TableColumn<Transaction, String> buyerColumn = new TableColumn<>("Buyer Name");
		buyerColumn.setCellValueFactory(cellData->cellData.getValue().buyerNameProperty());
		buyerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		buyerColumn.setOnEditCommit(event-> {
			Transaction transaction = event.getRowValue();
			transaction.setBuyerName(event.getNewValue());
			updateTransactionInDatabase(transaction);
		});
		
		// sold price column
		TableColumn<Transaction, Double> priceColumn = new TableColumn<>("Sold Price");
		priceColumn.setCellValueFactory(cellData->cellData.getValue().soldPriceProperty().asObject());
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		priceColumn.setOnEditCommit(event-> {
			Transaction transaction = event.getRowValue();
			transaction.setSoldPrice(event.getNewValue());
			updateTransactionInDatabase(transaction);
		});
		
		// category column
		TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
		categoryColumn.setCellValueFactory(cellData->cellData.getValue().categoryProperty());
		categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		categoryColumn.setOnEditCommit(event-> {
			Transaction transaction = event.getRowValue();
			transaction.setCategory(event.getNewValue());
			updateTransactionInDatabase(transaction);
		});
		
		// Book condition column
		// sold price column
		TableColumn<Transaction, String> conditionColumn = new TableColumn<>("Book Condition");
		conditionColumn.setCellValueFactory(cellData->cellData.getValue().bookConditionProperty());
		conditionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		conditionColumn.setOnEditCommit(event-> {
			Transaction transaction = event.getRowValue();
			transaction.setBookCondition(event.getNewValue());
			updateTransactionInDatabase(transaction);
		});
		
		dataTable.getColumns().addAll(sellerColumn, buyerColumn, priceColumn, categoryColumn, conditionColumn);
		
	}
	
	private void addContextMenu() {
		
		ContextMenu contextMenu = new ContextMenu();
		
		// edit option
		MenuItem editItem = new MenuItem("Edit");
		editItem.setOnAction(event-> {
			Transaction selectedTransaction = dataTable.getSelectionModel().getSelectedItem();
			if (selectedTransaction != null) {
				Alert editAlert = new Alert(Alert.AlertType.INFORMATION);
				editAlert.setTitle("Edit Record");
				editAlert.setHeaderText("You can edit the record directly in the table.");
				editAlert.showAndWait();
			}
		});
		
		// delete option
		MenuItem deleteItem = new MenuItem("Delete");
		deleteItem.setOnAction(event-> {
			Transaction selectedTransaction = dataTable.getSelectionModel().getSelectedItem();
			if (selectedTransaction != null) {
				transactions.remove(selectedTransaction);
				deleteTransactionFromDatabase(selectedTransaction);
			}
		});
		
		// add new record 
		MenuItem addItem = new MenuItem("Add new record");
		addItem.setOnAction(event-> addNewTransaction());
		
		contextMenu.getItems().addAll(editItem, deleteItem, addItem);
		dataTable.setContextMenu(contextMenu);
	}
	
	private void addNewTransaction() {
		Transaction newTransaction = new Transaction("New Seller", "New Buyer", 0.0, "N/A", "Good", null, java.time.LocalDateTime.now());
		transactions.add(newTransaction);
		updateTransactionInDatabase(newTransaction);
	}
	
	private void updateTransactionInDatabase(Transaction transaction) {
		System.out.println("Updating transaction: " + transaction);
		// add SQL update logic 
	}
	
	private void deleteTransactionFromDatabase(Transaction transaction) {
		System.out.println("Deleting transaction: " + transaction);
		// add SQL delete logic 
	}

}