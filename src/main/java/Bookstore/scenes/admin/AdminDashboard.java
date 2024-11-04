package Bookstore.scenes.admin;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class AdminDashboard{

	private ImageView imageSelected;
	private AnchorPane anchorPane;
	private TransactionView transactionView;
	private StatisticsView statisticsView;
	private String name;

	public AdminDashboard() {
		this.transactionView = new TransactionView();
		this.statisticsView = new StatisticsView();
		createUI();
	}
	public AdminDashboard(String name) {
		this.transactionView = new TransactionView();
		this.statisticsView = new StatisticsView();
		this.name = name;
		createUI();
	}
	public void setName(String name){
		this.name = name;
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

		// Transaction scene
		transactionButton.setOnAction(event -> {
			transactionButton.setStyle("-fx-background-color: Maroon");
			settingsButton.setStyle("-fx-background-color: Gray");
			statisticsButton.setStyle("-fx-background-color: Gray");
			displayTransactionView();
		});

		// Statistics scene
		statisticsButton.setOnAction(event -> {
			statisticsButton.setStyle("-fx-background-color: #CCCC00");
			transactionButton.setStyle("-fx-background-color :Gray");
			settingsButton.setStyle("-fx-background-color :Gray");
			displayStatisticsView();
		});

		// TODO: Create settings scene
		settingsButton.setOnAction(event -> {
			settingsButton.setStyle("-fx-background-color: Maroon");
			transactionButton.setStyle("-fx-background-color: Gray");
			statisticsButton.setStyle("-fx-background-color: Gray");
		});

		// Define events for each button
		transactionButton.setOnAction(event -> displayTransactionView());

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
		System.out.println(name);
		Text userName = new Text(name);
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
		Button userImageUpload = new Button("Upload Image");
		userImageUpload.setOnAction(event -> uploadUserImage(anchorPane));
		AnchorPane.setTopAnchor(userImageUpload, 200.0);
		AnchorPane.setLeftAnchor(userImageUpload, 10.0);


		userInfoStack.getChildren().addAll(userInfo, userName, userInfoBox, userImage, userImageUpload);
	}

	// Extend to allow user to choose image from computer to upload for profile picture
	// TODO: User image doesn't upload to profile picture.
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
			// was userImage
			imageSelected = new ImageView(profilePic);
			// Image dimensions
			imageSelected.setFitWidth(40);
			imageSelected.setFitHeight(40);
			imageSelected.setPreserveRatio(true);

		}

	}

	private void displayTransactionView() {
		transactionView = new TransactionView();
		AnchorPane transactionPane = transactionView.getRoot();
		anchorPane.getChildren().removeIf(node -> node instanceof AnchorPane && node != getRoot());
		AnchorPane.setTopAnchor(transactionPane, 300.0);
		AnchorPane.setLeftAnchor(transactionPane, 50.0);
		AnchorPane.setBottomAnchor(transactionPane, 250.0);

		anchorPane.getChildren().add(transactionPane);
	}

	private void displayStatisticsView() {

		// Define button color change

		anchorPane.getChildren().removeIf(node -> node instanceof AnchorPane && node != getRoot());

		AnchorPane statisticsPane = statisticsView.getRoot();
		AnchorPane.setTopAnchor(statisticsPane, 300.0);
		AnchorPane.setLeftAnchor(statisticsPane, 50.0);
		AnchorPane.setBottomAnchor(statisticsPane, 250.0);

		anchorPane.getChildren().add(statisticsPane);
	}
}

