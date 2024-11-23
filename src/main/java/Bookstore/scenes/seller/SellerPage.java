package Bookstore.scenes.seller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SellerPage {

    @FXML
    private Button inventoryButton;

    @FXML
    private Button sellingProcessButton;

    @FXML
    private VBox outerContentArea;

    @FXML
    public void initialize() {
        showInventory();
    }

    @FXML
    private void showInventory() {
        try {
            Node inventoryView = FXMLLoader.load(getClass().getResource("/Bookstore/scenes/seller/SellerInventory.fxml"));
            outerContentArea.getChildren().setAll(inventoryView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showSellingProcess() {
        try {
            Node sellingProcessView = FXMLLoader.load(getClass().getResource("/Bookstore/scenes/seller/SellingProcess.fxml"));
            outerContentArea.getChildren().setAll(sellingProcessView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}