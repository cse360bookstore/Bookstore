package Bookstore.scenes.admin;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TransactionView {
	private AnchorPane rootPane;

	public TransactionView() {
		initializeUI();
	}

	public AnchorPane getRoot() {
		return rootPane;
	}

	@SuppressWarnings("unchecked")
	private void initializeUI() {
		rootPane = new AnchorPane();

		// Show transaction data
		TableView<CustomerData> dataTable = new TableView<>();

		// Username Column
		TableColumn<CustomerData, String> usernameColumn = new TableColumn<>("Username");
		usernameColumn.setMinWidth(230);
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

		// Category Column
		TableColumn<CustomerData, String> categoryColumn = new TableColumn<>("Category");
		categoryColumn.setMinWidth(230);
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

		// Price Column
		TableColumn<CustomerData, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(230);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		dataTable.getColumns().addAll(usernameColumn, categoryColumn, priceColumn);


		// Add code for getting data from database to add to table
		//TODO: Sample data doesn't display on table, needs fixing
		dataTable.getItems().add(new CustomerData("User", "Used Like New", 10.00));
		dataTable.getItems().add(new CustomerData("User", "Moderately Used", 15.00));
		dataTable.getItems().add(new CustomerData("User", "Heavily Used", 20.00));


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