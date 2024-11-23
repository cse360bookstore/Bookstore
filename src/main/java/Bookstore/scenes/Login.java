package Bookstore.scenes;

import Bookstore.BuyerPage;
import Bookstore.SellerPage;
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
import javafx.scene.control.ComboBox;
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
    
    @FXML
    private ComboBox<String> userRole;

    public void initialize() {
        // Example: Adding additional items dynamically
        userRole.getItems().addAll("Buyer", "Seller", "Admin");
    }

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

                if (loggedin) 
                {
                	Stage stage = (Stage) usernameField.getScene().getWindow();
                    if("Buyer".equals(userRole.getValue()))
                    {
                    	try {
							FXMLLoader fxmlLoader = new FXMLLoader(BuyerPage.class.getResource("/Bookstore/scenes/buyer/BuyingProcess.fxml"));
							Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
							stage.setTitle("Buyer Page");
							stage.setScene(scene);
							stage.show();
						} catch (IOException e) {
							e.printStackTrace();
							System.err.println("Error loading the Buyer page: " + e.getMessage());
						}
                    }
                    else if("Seller".equals(userRole.getValue()))
                    {
                    	try {
							System.out.println("open seller page");
							System.out.println("username: " + username);
							FXMLLoader fxmlLoader = new FXMLLoader(SellerPage.class.getResource("/Bookstore/scenes/seller/SellingProcess.fxml"));

							Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
							stage.setTitle("Seller Page");
							stage.setScene(scene);
							stage.show();
						} catch (IOException e) {
							e.printStackTrace();
							System.err.println("Error loading the Seller page: " + e.getMessage());
						}
                    }
                    else if("Admin".equals(userRole.getValue()))
                    {
                         try {
                             AdminDashboard adminDash = new AdminDashboard(username);
                             Scene scene = new Scene(adminDash.getRoot(), 800, 1200);
                             stage.setTitle("Admin Page");
                             stage.setScene(scene);
                             stage.show();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                System.err.println("Error loading the Admin page: " + e.getMessage());
                            }
                    }
                 }

                
            }

        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Invalid username, password, or user role", "Please enter a valid username/password");
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