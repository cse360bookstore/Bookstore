package Bookstore.scenes;

import Bookstore.BuyerPage;
import Bookstore.SellerPage;
import Bookstore.scenes.admin.AdminDashboard;
import Bookstore.scenes.seller.SellingProcess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainMenu {
    private String username;

    public void setUsername(String username) {
        this.username = username;
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
        SellingProcess test  = fxmlLoader.getController();
        test.setUsername(username);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void openBuyerPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(BuyerPage.class.getResource("/Bookstore/scenes/BuyingProcess.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
        stage.setTitle("Buyer Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openAdminPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminDashboard adminDash = new AdminDashboard(username);
        Scene scene = new Scene(adminDash.getRoot(), 800, 1200);
        stage.setTitle("Admin Page");
        stage.setScene(scene);
        stage.show();
    }
}