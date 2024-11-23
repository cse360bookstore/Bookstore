package Bookstore.scenes.admin;

import java.io.File;
import java.sql.SQLException;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.dataManagers.AdminManager;
import Bookstore.dataManagers.BookManager;
import Bookstore.models.BookForSale;
import Bookstore.models.UserRole;
import Bookstore.models.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox; import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.sql.DataSource;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AdminDashboard{

	private ImageView imageSelected;
	private AnchorPane anchorPane;
	private TransactionView transactionView;
	private StatisticsView statisticsView;
	private BookForSaleView bookForSaleView;
	private String name;
	private StackPane userInfoStack;
	private Circle userImage;
	DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
	private final AdminManager adminManager = new AdminManager(dataSource);
	private AnchorPane contentArea;
	
	public AdminDashboard() throws SQLException {
		this.transactionView = new TransactionView();
		this.statisticsView = new StatisticsView();
		createUI();
	}
	public AdminDashboard(String name) throws SQLException {
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
            try {
                displayTransactionView();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

		settingsButton.setOnAction(event -> {
			settingsButton.setStyle("-fx-background-color: Maroon");
			transactionButton.setStyle("-fx-background-color: Gray");
			statisticsButton.setStyle("-fx-background-color: Gray");

			contentArea.getChildren().clear();

			Settings settingsView = new Settings(this);
			AnchorPane settingsPane = settingsView.getRoot();
			setContentArea(settingsPane);
        });


		// Define events for each button
		transactionButton.setOnAction(event -> {
            try {
                displayTransactionView();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

		// Keep buttons organized horizontally
		HBox hBox = new HBox(80);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(transactionButton, statisticsButton, settingsButton);
		AnchorPane.setTopAnchor(hBox, 250.0);
		AnchorPane.setLeftAnchor(hBox, 200.0);
		anchorPane.getChildren().add(hBox);

	    // Stack all user info box
		userInfoStack = new StackPane();

		// Top left user info box
		Rectangle userInfo = new Rectangle(150.0f, 200.0f, Color.MAROON);
		userInfo.setArcHeight(10.0d);
		userInfo.setArcWidth(10.0d);

		// User name text and alignment
		UserSession session = UserSession.getInstance();
		Text userName = new Text(session.getUsername());
		StackPane.setAlignment(userName, Pos.TOP_CENTER); 
		StackPane.setMargin(userName, new Insets(140, 0, 0, 0)); 
		userName.setFill(Color.WHITE);
		
		// back button
		Button back = new Button("Back");
		back.setOnAction(event -> {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();       
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/scenes/login/Login.fxml"));
	    Scene scene = null;
		try {
				scene = new Scene(fxmlLoader.load(), 800, 600);
			} catch (IOException e) {

				e.printStackTrace();
			}
			    stage.setScene(scene);
			    stage.show();
		    });
		    AnchorPane.setTopAnchor(back, 10.0);
			AnchorPane.setRightAnchor(back, 10.0);
			anchorPane.getChildren().add(back);

		VBox userInfoBox = new VBox();
		userInfoBox.setAlignment(Pos.BOTTOM_CENTER);
		userInfoBox.getChildren().addAll(userName);
		        
		// Stack user info
		userInfoStack.setAlignment(Pos.TOP_LEFT);
		anchorPane.getChildren().add(userInfoStack);

		// Create user image placeholder
		userImage = new Circle(50);
		StackPane.setMargin(userImage, new Insets(0, 0, 20, 0));
		userImage.setFill(Color.YELLOW);
		StackPane.setAlignment(userImage, Pos.CENTER);

		// Add button to allow user image upload
		Button userImageUpload = new Button("Upload Image");
		userImageUpload.setOnAction(event -> uploadUserImage(anchorPane));
		StackPane.setAlignment(userImageUpload, Pos.BOTTOM_CENTER);
		StackPane.setMargin(userImageUpload, new Insets(10, 0, 10, 0));
		AnchorPane.setTopAnchor(userImageUpload, 200.0);
		AnchorPane.setLeftAnchor(userImageUpload, 10.0);

		userInfoStack.getChildren().addAll(userInfo, userName, userInfoBox, userImage, userImageUpload);

		contentArea = new AnchorPane();
		contentArea.setPrefSize(600, 400);
		AnchorPane.setTopAnchor(contentArea, 350.0);
		AnchorPane.setLeftAnchor(contentArea, 50.0);
		AnchorPane.setRightAnchor(contentArea, 50.0);
		AnchorPane.setBottomAnchor(contentArea, 50.0);
		anchorPane.getChildren().add(contentArea);
		
	}

	private void uploadUserImage(AnchorPane anchorPane2) {
		// Select image file
		FileChooser chooseImage = new FileChooser();
		chooseImage.setTitle("Choose Image");
		// Specify accepted file type
		chooseImage.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", ".jpeg"));

		File selectedFile = chooseImage.showOpenDialog(anchorPane.getScene().getWindow());
		if(selectedFile != null) {
			
			// Set image file as profile picture
			Image profilePic = new Image(selectedFile.toURI().toString());
			imageSelected = new ImageView(profilePic);
			
			// Image dimensions
			double diameter = userImage.getRadius() * 2;
			imageSelected.setFitWidth(diameter);
			imageSelected.setFitHeight(diameter);
			imageSelected.setPreserveRatio(false);
		
			// clip image to fit circle 
			Circle clipCircle = new Circle(userImage.getRadius());
			clipCircle.setCenterX(userImage.getRadius());
			clipCircle.setCenterY(userImage.getRadius());
			imageSelected.setClip(clipCircle);
		
			// remove existing placeholder 
			userInfoStack.getChildren().removeIf(node -> node instanceof ImageView);
		
			// add clipped image 
			userInfoStack.getChildren().add(imageSelected);
			StackPane.setAlignment(imageSelected, Pos.CENTER);
			
			imageSelected.setTranslateY(-10);
			
		}
	}

	private void displayTransactionView() throws SQLException {
		contentArea.getChildren().clear();

		transactionView = new TransactionView();
		AnchorPane transactionPane = transactionView.getRoot();
		setContentArea(transactionPane);
	}

	private void displayStatisticsView() {
		contentArea.getChildren().clear();
		statisticsView = new StatisticsView();
		AnchorPane statisticsPane = statisticsView.getRoot();
		setContentArea(statisticsPane);
	}

	private void displaySettingsView() {
		contentArea.getChildren().clear();
		Settings settingsView = new Settings(this);
		AnchorPane settingsPane = settingsView.getRoot();
		setContentArea(settingsPane);

	}

	private void setContentArea(Node node) {
		contentArea.getChildren().add(node);
		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);
	}
	public void setContent(Node node) {
		contentArea.getChildren().clear();
		setContentArea(node);
	}
}

