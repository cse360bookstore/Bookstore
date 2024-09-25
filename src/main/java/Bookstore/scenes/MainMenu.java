package Bookstore.scenes;

import Bookstore.BuyerPage;
import Bookstore.SellerPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainMenu {

    // Method to open the Seller Page
    @FXML
    private void openSellerPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SellerPage.class.getResource("/Bookstore/scenes/SellingProcess.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
        stage.setTitle("Seller Page");
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
}