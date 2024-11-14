package Bookstore.scenes;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.AlertHelper;
import Bookstore.dataManagers.AuthManager;
import Bookstore.models.UserRole;
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
        String email = "email_3";
        String firstname = "new_user";
        String lastname = "new_user";
        try{
            UserSession user =  authManager.register(username, password, email, firstname, lastname, UserRole.BUYER_SELLER_ADMIN);
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
           // boolean loggedin  = registertest();

            boolean loggedin = authManager.login(username, password);



            if (loggedin) {
                System.out.println("Logged in successfully");
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println("testing get email");
                user = UserSession.getInstance();

                // todo: load a different main menu depending on roles returned here
                System.out.println("User Roles: " + user.getUserRole());

                // todo: refactor this to use in forgot password
                /*
                String email = authManager.getEmailFromUserName(username);
                String email2 = user.getEmail();
                System.out.println("Email from sp: " + email);
                System.out.println("Email from logging in: " + email2);

                String newpass = "s";
                boolean loggedin2 = authManager.login(username, newpass);

                if (!loggedin2) {
                    System.out.println("Logged in didnt work with new pass");
                }

                boolean updated = authManager.updatePassword(4, newpass);

                // after updating password,
                if (updated) {
                    System.out.println("Password updated");
                    boolean loggedin3 = authManager.login(username, newpass);
                    if (loggedin3) {
                        System.out.println("Logged in successfully with the new password");
                    }
                }

                 */

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