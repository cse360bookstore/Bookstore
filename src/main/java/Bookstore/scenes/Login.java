package Bookstore.scenes;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Login {
    private String username;
    private String password;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;



    @FXML
    private void handleLogin() {
        username = usernameField.getText();
        password = passwordField.getText();

        // todo: validate username and password
        if (!username.isEmpty() && !password.isEmpty()) {
            // todo: only load page person selects and is validated for
            loadMainMenu();
        } else {
            System.out.println("Invalid login credentials");
        }
    }

    private void loadMainMenu() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/scenes/MainMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainMenu controller = fxmlLoader.getController();
            controller.setUsername(username);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}