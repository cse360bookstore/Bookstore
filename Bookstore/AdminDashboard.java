package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminDashboard{
	
	private SceneController controller;
	private AnchorPane anchorPane;
	
	public AdminDashboard(SceneController controller) {
		this.controller = controller;
		createUI();
	}
	
	public AnchorPane getRoot() {
		return anchorPane;
	}
	
	private void createUI() {
		anchorPane = new AnchorPane();
		
		Rectangle r1 = new Rectangle(300.0f, 80.0f, Color.MAROON);
	    //r1.setArcHeight(10.0d);
		//r1.setArcWidth(10.0d);
		
		// Admin Dash 
		Text adminDash = new Text("Admin Dashboard");
		adminDash.setFill(Color.WHITE);
		adminDash.setFont(Font.font("Arial", 28));
	
		// Stack admin text and rectangle
		StackPane stack = new StackPane();
		stack.getChildren().addAll(r1, adminDash);
		AnchorPane.setTopAnchor(stack, 75.0);
		AnchorPane.setLeftAnchor(stack, 250.0);
		anchorPane.getChildren().add(stack);
		
		// Transaction Button 
		Button transactionButton = new Button("Transactions");
		transactionButton.setTextFill(Color.WHITE);
		transactionButton.setStyle("-fx-background-color: Maroon");
		
		// Statistics Button
		Button statisticsButton = new Button("Statistics");
		statisticsButton.setTextFill(Color.BLACK);
		statisticsButton.setStyle("-fx-background-color: #CCCC00");
	
		// Settings Button
		Button settingsButton = new Button("Settings");
		settingsButton.setTextFill(Color.WHITE);
		settingsButton.setStyle("-fx-background-color: Maroon");
		
		// Keep buttons organized horizontally 
		HBox hBox = new HBox(80);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(transactionButton, statisticsButton, settingsButton);
		AnchorPane.setTopAnchor(hBox, 250.0);
		AnchorPane.setLeftAnchor(hBox, 200.0);
		anchorPane.getChildren().add(hBox);
	
		// Top left user info box
		Rectangle userInfo = new Rectangle(180.0f, 200.0f, Color.MAROON);
		userInfo.setArcHeight(10.0d);
		userInfo.setArcWidth(10.0d);

		// Username text
		Text userName = new Text("User@account");
		userName.setFill(Color.WHITE);

		// 
		VBox userInfoBox = new VBox();
		userInfoBox.setAlignment(Pos.BOTTOM_CENTER);
		userInfoBox.getChildren().addAll(userName);
		
		// Make main scene 800, 850
	
	}
}
