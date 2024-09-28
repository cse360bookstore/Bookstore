package application;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AdminDashboard{

	private ImageView userImage;
	private SceneController controller;
	private AnchorPane anchorPane;
	private TransactionView transactionView;
	private StatisticsView statisticsView;
	
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
	    r1.setArcHeight(10.0d);
		r1.setArcWidth(10.0d);
		
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
		
		// Define events for each button
		// TODO: Allow user to select between button, only allows one click 
		transactionButton.setOnAction(event -> displayTransactionView());
		statisticsButton.setOnAction(event -> displayStatisticsView());
		
		// Keep buttons organized horizontally 
		HBox hBox = new HBox(80);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(transactionButton, statisticsButton, settingsButton);
		AnchorPane.setTopAnchor(hBox, 250.0);
		AnchorPane.setLeftAnchor(hBox, 200.0);
		anchorPane.getChildren().add(hBox);

	    // Stack all user info box 	
		StackPane userInfoStack = new StackPane();
		
		// Top left user info box
		Rectangle userInfo = new Rectangle(150.0f, 150.0f, Color.MAROON);
		userInfo.setArcHeight(10.0d);
		userInfo.setArcWidth(10.0d);

		// Username text
		Text userName = new Text("User@account");
		StackPane.setAlignment(userName, Pos.BOTTOM_CENTER);
		AnchorPane.setTopAnchor(userInfoStack, 10.0);
		AnchorPane.setLeftAnchor(userInfoStack, 10.0);
		StackPane.setMargin(userName, new Insets(0, 0, 20, 0));
		userName.setFill(Color.WHITE);

		VBox userInfoBox = new VBox();
		userInfoBox.setAlignment(Pos.BOTTOM_CENTER);
		userInfoBox.getChildren().addAll(userName);
		
		// Stack user info 
		userInfoStack.setAlignment(Pos.TOP_LEFT);
		anchorPane.getChildren().add(userInfoStack);
		
		// Create user image placeholder 
		Circle userImage = new Circle(50);
		StackPane.setMargin(userImage, new Insets(0, 0, 20, 0));
		userImage.setFill(Color.YELLOW);
		StackPane.setAlignment(userImage, Pos.CENTER);
	
		// Add all children objects to stack 
		
		// Add button to allow user image upload
		// TODO: Center this button so it's below circle placeholder and above user email
		Button userImageUpload = new Button("Upload Iamge");
		userImageUpload.setOnAction(event -> uploadUserImage(anchorPane));
		AnchorPane.setTopAnchor(userImageUpload, 200.0);
		AnchorPane.setLeftAnchor(userImageUpload, 10.0);
		
	
		userInfoStack.getChildren().addAll(userInfo, userName, userInfoBox, userImage, userImageUpload);
	}
	
	// Extend to allow user to choose image from computer to upload for profile picture
	// TODO: After user selects file to use as profile picture, it fails to display image. 
	private void uploadUserImage(AnchorPane anchorPane2) {
		// Select image file
		FileChooser chooseImage = new FileChooser();
		chooseImage.setTitle("Choose Image");
		// Specify accepted file type
		chooseImage.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
		
		File selectedFile = chooseImage.showOpenDialog(anchorPane.getScene().getWindow());
		if(selectedFile != null) {
			// Set image file as profile picture 
			Image profilePic = new Image(selectedFile.toURI().toString());
			userImage = new ImageView(profilePic);
			// Image dimensions
			userImage.setFitWidth(40);
			userImage.setFitHeight(40);
			userImage.setPreserveRatio(true);
			
		}
		
	}
	
	private void displayTransactionView() {
		transactionView = new TransactionView();
		AnchorPane transactionPane = transactionView.getRoot();
		anchorPane.getChildren().add(transactionPane);
	}
	
	private void displayStatisticsView() {
		statisticsView = new StatisticsView();
		AnchorPane statisticsPane = statisticsView.getRoot();
		anchorPane.getChildren().add(statisticsPane);
	}
}

