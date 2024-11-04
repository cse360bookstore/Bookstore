package Bookstore.scenes.admin;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.dataManagers.AdminManager;
import Bookstore.models.Book;
import Bookstore.models.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDateTime;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionView {
	private AnchorPane rootPane;

	public TransactionView() throws SQLException {
		initializeUI();
	}

	public AnchorPane getRoot() {
		return rootPane;
	}
	DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
	private final AdminManager adminManager = new AdminManager(dataSource);


	@SuppressWarnings("unchecked")
	private void initializeUI() throws SQLException {
		rootPane = new AnchorPane();

		TableView<Transaction> dataTable = new TableView<>();

		TableColumn<Transaction, String> sellerNameColumn = new TableColumn<>("Seller Name");
		sellerNameColumn.setMinWidth(150);
		sellerNameColumn.setCellValueFactory(new PropertyValueFactory<>("sellerName"));

		// Buyer Name Column
		TableColumn<Transaction, String> buyerNameColumn = new TableColumn<>("Buyer Name");
		buyerNameColumn.setMinWidth(150);
		buyerNameColumn.setCellValueFactory(new PropertyValueFactory<>("buyerName"));

		// Sold Price Column
		TableColumn<Transaction, Double> soldPriceColumn = new TableColumn<>("Sold Price");
		soldPriceColumn.setMinWidth(100);
		soldPriceColumn.setCellValueFactory(new PropertyValueFactory<>("soldPrice"));

		// Category Column
		TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
		categoryColumn.setMinWidth(150);
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

		// Book Title Column
		TableColumn<Transaction, String> bookTitleColumn = new TableColumn<>("Book Title");
		bookTitleColumn.setMinWidth(200);
		bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

		// Sold Date Column
		TableColumn<Transaction, LocalDateTime> soldDateColumn = new TableColumn<>("Sold Date");
		soldDateColumn.setMinWidth(150);
		soldDateColumn.setCellValueFactory(new PropertyValueFactory<>("soldDate"));

		dataTable.getColumns().addAll(
				sellerNameColumn,
				buyerNameColumn,
				soldPriceColumn,
				categoryColumn,
				bookTitleColumn,
				soldDateColumn
		);

		ObservableList< Transaction > transactions = adminManager.getAllTransactions();

		dataTable.setItems(transactions);

		// Position table in center
		//AnchorPane.setTopAnchor(dataTable, 300.0);
		//AnchorPane.setLeftAnchor(dataTable, 50.0);
		//AnchorPane.setBottomAnchor(dataTable, 250.0);

		rootPane.getChildren().add(dataTable);

	}

	// Helper class to hold database data
	public static class CustomerData{
		private String username;
		private String category;
		private Double price;

		public CustomerData(String username, String category, Double price) {
			this.username = username;
			this.category = category;
			this.price = price;

		}

		// Getters for table
		public String getUsername() {
			return username;
		}

		public String getCategory() {
			return category;
		}

		public Double getPrice(){
			return price;
		}

	}
}