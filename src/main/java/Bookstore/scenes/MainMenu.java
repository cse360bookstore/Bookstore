package Bookstore.scenes;

import Bookstore.BuyerPage;
import Bookstore.SellerPage;
import Bookstore.models.UserSession;
import Bookstore.scenes.admin.AdminDashboard;
import Bookstore.scenes.buyer.BuyingProcess;
import Bookstore.scenes.seller.SellingProcess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

// Todo: Have user session keep track of what pages they can open, and only display
public class MainMenu {
    private String username;
    private UserSession user;
    public void setUsername(String username) {
        this.username = username;
    }
    public void setUser(UserSession user) {
        this.user = user;
    }
    // Method to open the Seller Page
    @FXML
    private void openSellerPage(ActionEvent event) throws IOException {
        System.out.println("open seller page");
        System.out.println("username: " + username);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(SellerPage.class.getResource("/Bookstore/scenes/seller/SellingProcess.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
        stage.setTitle("Seller Page");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void openBuyerPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(BuyerPage.class.getResource("/Bookstore/scenes/buyer/BuyingProcess.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
        stage.setTitle("Buyer Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openAdminPage(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminDashboard adminDash = new AdminDashboard(username);
        Scene scene = new Scene(adminDash.getRoot(), 800, 1200);
        stage.setTitle("Admin Page");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/scenes/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}