package Bookstore.scenes.admin;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import Bookstore.dataManagers.AdminManager;

import Bookstore.models.BookWithUser;

public class Settings {

	private AnchorPane rootPane;
    private AdminDashboard parentDashboard;

	public Settings() {
		initializeUI();
	}
    public Settings(AdminDashboard parentDashboard) {
        this.parentDashboard = parentDashboard;
        initializeUI();
    }
	public AnchorPane getRoot() {
		return rootPane;

	}

	private void initializeUI() {

		rootPane = new AnchorPane();

		// Button to edit seller input.
		Button editBuyerSellerButton = new Button("Edit Buyer/Seller Input");
		editBuyerSellerButton.setTextFill(Color.WHITE);
		editBuyerSellerButton.setStyle("-fx-background-color: Black");
		editBuyerSellerButton.setOnAction(e -> {
            System.out.println("Redirect to Edit/Seller page");
            displayBookForSaleView();
        });

        // button to edit user roles
        Button editUserRolesButton = new Button("Edit User Roles");
        editUserRolesButton.setTextFill(Color.BLACK);
        editUserRolesButton.setStyle("-fx-background-color: #CCCC00");
        editUserRolesButton.setOnAction(e -> {
            // TODO: Redirect to User Roles edit page
            System.out.println("Redirect to Edit User Roles page");
            displayUserRoleManagerView();
        });

		// Keep the buttons aligned
		HBox buttonLayout = new HBox(80);
		buttonLayout.setAlignment(Pos.CENTER);
		buttonLayout.getChildren().addAll(editBuyerSellerButton, editUserRolesButton);

        // position top center
        AnchorPane.setTopAnchor(buttonLayout, 50.0);
		AnchorPane.setLeftAnchor(buttonLayout, 200.0);
        AnchorPane.setRightAnchor(buttonLayout, 200.0);

		rootPane.getChildren().add(buttonLayout);
	}

    private void displayBookForSaleView() {
        try {
            BookForSaleView bookForSaleView = new BookForSaleView();
            parentDashboard.setContent(bookForSaleView.getRoot());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayUserRoleManagerView() {
        UserRoleManagerView userRoleManagerView = new UserRoleManagerView();
        parentDashboard.setContent(userRoleManagerView.getRoot());
    }
}
