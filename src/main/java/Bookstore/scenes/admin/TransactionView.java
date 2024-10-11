package Bookstore.scenes.admin;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class TransactionView {
	private AnchorPane rootPane;
	
	public TransactionView() {
		initializeUI();
	}
	
	public AnchorPane getRoot() {
		return rootPane;
	}
	
	private void initializeUI() {
		rootPane = new AnchorPane();
	
		// SHow transaction data
		TableView<TransactionData> table = new TableView<>();
		
		// Username Column 
		TableColumn<TransactionData, String> usernameColumn = new TableColumn<>("Username");
		usernameColumn.setMinWidth(150);
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		// Category Column
		TableColumn<TransactionData, String> categoryColumn = new TableColumn<>("Category");
		categoryColumn.setMinWidth(150);
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		
		// Price Column 
		TableColumn<TransactionData, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(150);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		table.getColumns().addAll(usernameColumn, categoryColumn, priceColumn);
		
		// Add code for getting data from database to add to table
		
		// Position table in center 
		//AnchorPane.setTopAnchor(table, 100.0);
		//AnchorPane.setLeftAnchor(table, 100.0);
		AnchorPane.setBottomAnchor(table, 100.0);
		
		rootPane.getChildren().add(table);
	}
	
	// Helper class to hold database data
	public class TransactionData{
		private String username;
		private String category;
		private Double price;
		
		public TransactionData(String Username, String Category, Double Price) {
			this.username = Username;
			this.category = Category;
			this.price = Price;
			
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



