package Bookstore.scenes.buyer;

import Bookstore.components.AlertHelper;
import Bookstore.dataManagers.BookManager;
import Bookstore.models.BookWithUser;
import Bookstore.models.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class BookItem {
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Button buyButton;

    private BookWithUser book;
    private BookManager bookManager;

    public void setBook(BookWithUser book, BookManager bookManager) {
        this.book = book;
        this.bookManager = bookManager;

        titleLabel.setText(book.getTitle());
        authorLabel.setText("Author: " + book.getAuthor());
        priceLabel.setText(String.format("Price: $%.2f", book.getPrice()));

        buyButton.setOnAction(event -> handleBuyAction());
    }

    private void handleBuyAction() {

        Alert confirmAlert = AlertHelper.createAlert(Alert.AlertType.CONFIRMATION, "Confirm Purchase", "Are you sure you want to buy \"" + book.getTitle() + "\" for $" + book.getPrice() + "?");
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    boolean success = bookManager.buyBook(book.getBookID(), getCurrentUserId(), book.getPrice());
                    if (success) {
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Purchase Successful", "You have successfully purchased \"" + book.getTitle() + "\".");

                        buyButton.setDisable(true);

                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, "Purchase Failed", "Failed to purchase the book. It might have already been sold.");
                        buyButton.setDisable(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    AlertHelper.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing your purchase.");
                }
            }
        });
    }


    private int getCurrentUserId() {
        return UserSession.getInstance().getUserId();
    }


}