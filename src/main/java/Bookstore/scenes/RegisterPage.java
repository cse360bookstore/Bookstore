package Bookstore.scenes;


import Bookstore.Main;
import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.AlertHelper;
import Bookstore.dataManagers.AuthManager;
import Bookstore.models.UserRole;
import Bookstore.models.UserSession;
import Bookstore.scenes.admin.AdminDashboard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterPage{
	private String username, password, email, firstName, lastName;
	private UserRole role;
	DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
	private final AuthManager authManager = new AuthManager(dataSource);
	
	@FXML
	private TextField regUsername;
	
	@FXML
	private TextField regPassword;
	
	@FXML
	private TextField regEmail;
	
	@FXML
	private TextField regFirstName;
	
	@FXML
	private TextField regLastName;
	
	@FXML
	private RadioButton buyerChoice;
	
	@FXML
	private RadioButton sellerChoice;
	
	@FXML
	private Button registerButton;
	
	@FXML
	private void handleRegistry() {
		username = regUsername.getText();
		password = regPassword.getText();
		email = regEmail.getText();
		firstName = regFirstName.getText();
		lastName = regLastName.getText();
		
		if(buyerChoice.isSelected() && sellerChoice.isSelected()) 
			role = UserRole.BUYER_SELLER;
		else if(buyerChoice.isSelected()) 
			role = UserRole.BUYER;
		else if(sellerChoice.isSelected())
			role = UserRole.SELLER;

		try{
            UserSession user =  authManager.register(username, password, email, firstName, lastName, role);
        }
        catch (RuntimeException e){
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error Registering user", e.getMessage());
        }
		
		Stage stage = (Stage) regUsername.getScene().getWindow();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Bookstore/scenes/login/Login.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 800, 600);
			stage.setTitle("Main Menu");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}