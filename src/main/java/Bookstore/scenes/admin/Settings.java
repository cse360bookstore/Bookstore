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
    private AdminManager adminManager;

	public Settings() {
		initializeUI();
	}
	public AnchorPane getRoot() {
		return rootPane;
		
	}
	
	private void initializeUI() {
		
		rootPane = new AnchorPane();
	
		// Button to edit seller input. 
		Button editBuyerSellerButton = new Button("Edit Buyer/Selelr Input");
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

    private void displayBookForSaleView(){
        try{
            BookForSaleView bookForSaleView = new BookForSaleView();
            Scene scene = rootPane.getScene();
            if (scene != null){
                scene.setRoot(bookForSaleView.getRoot());
            }else{
                System.out.println("Error: Scene is not set. Cannot switch to BookForSaleView.");
            }
        } catch(SQLException e){
            e.printStackTrace();
            System.err.println("Error loading BookForSaleView: " + e.getMessage());
        }
    }

    private void displayUserRoleManagerView(){
        try{
            UserRoleManagerView userRoleManagerView = new UserRoleManagerView();

            Scene scene = rootPane.getScene();
            if (scene != null){
                scene.setRoot(userRoleManagerView.getRoot());
            }else{
                System.out.println("Error: Scene is not set. Cannot switch to UserRoleManagerView.");
            }
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("Error loading UserRoleManagerView: " + e.getMessage());
        }
    }
}
