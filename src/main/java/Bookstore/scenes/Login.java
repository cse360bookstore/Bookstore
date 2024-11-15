package Bookstore.scenes;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.AlertHelper;
import Bookstore.dataManagers.AuthManager;
import Bookstore.models.UserSession;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class Login {
    private String username;
    private String password;
    private UserSession user;
    DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
    private final AuthManager authManager = new AuthManager(dataSource);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    // Todo: make a form to register users
/*
    public boolean registertest() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = "email_2";
        String firstname = "firstname";
        String lastname = "lastname";
        try{
            UserSession user =  authManager.register(username, password, email, firstname, lastname);
        }
        catch (RuntimeException e){
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error Registering user", e.getMessage());

        }

        if (user != null) {
            return true;
        }
        return false;

    }
*/

    
    @FXML
    private void handleLogin() {
        username = usernameField.getText();
        password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            // todo: only load page person selects and is validated for
            //boolean loggedin  = registertest();

            boolean loggedin = authManager.login(username, password);



            if (loggedin) {
                System.out.println("Logged in successfully");
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println(user);

                loadMainMenu();
            }

        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Invalid username or password", "Please enter a valid username/password");
        }
    }


    private void loadMainMenu() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/scenes/MainMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainMenu controller = fxmlLoader.getController();
            controller.setUser(user);
            controller.setUsername(username);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	}