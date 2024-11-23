package Bookstore.scenes.buyer;

import java.util.HashSet;
import java.util.Set;

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
    private Button addToCartButton;
    @FXML
    private Button removeFromCartButton;

    private BookWithUser book;
    private BookManager bookManager;
    private static Set<Integer> shoppingCart = new HashSet<>();
    private BuyingProcess buyingProcessController;

    public void setBook(BookWithUser book, BookManager bookManager, BuyingProcess buyingProcessController) {
        this.book = book;
        this.bookManager = bookManager;
        this.buyingProcessController = buyingProcessController;

        titleLabel.setText(book.getTitle());
        authorLabel.setText("Author: " + book.getAuthor());
        priceLabel.setText(String.format("Price: $%.2f", book.getPrice()));

        addToCartButton.setOnAction(event -> handleAddToCartAction());
        removeFromCartButton.setOnAction(event -> handleRemoveFromCartAction());


        if (shoppingCart.contains(book.getBookID())) {
            addToCartButton.setVisible(false);
            removeFromCartButton.setVisible(true);
        }
        else {
            addToCartButton.setVisible(true);
            removeFromCartButton.setVisible(false);
        }
    }

    private void handleAddToCartAction() {
        int bookId = book.getBookID();
        if (shoppingCart.contains(bookId)){
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Item Already in Cart", "Book has already been added.");
            return;
        }

        shoppingCart.add(bookId);
        buyingProcessController.updateCartItemCount();
//        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Success", "Successfully added to your cart.");
        addToCartButton.setVisible(false);
        removeFromCartButton.setVisible(true);
    }

    private void handleRemoveFromCartAction(){
        int bookId = book.getBookID();
        shoppingCart.remove(bookId);
        buyingProcessController.updateCartItemCount();
        addToCartButton.setVisible(true);
        removeFromCartButton.setVisible(false);
    }

    public static Set<Integer> getShoppingCart(){
        return shoppingCart;
    }

//    private void handleBuyAction() {
//
//        Alert confirmAlert = AlertHelper.createAlert(Alert.AlertType.CONFIRMATION, "Confirm Purchase", "Are you sure you want to buy \"" + book.getTitle() + "\" for $" + book.getPrice() + "?");
//        confirmAlert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                try {
//                    boolean success = bookManager.buyBook(book.getBookID(), getCurrentUserId(), book.getPrice());
//                    if (success) {
//                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Purchase Successful", "You have successfully purchased \"" + book.getTitle() + "\".");
//
//                        addToCartButton.setDisable(true);
//
//                    } else {
//                        AlertHelper.showAlert(Alert.AlertType.ERROR, "Purchase Failed", "Failed to purchase the book. It might have already been sold.");
//                        addToCartButton.setDisable(true);
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    AlertHelper.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing your purchase.");
//                }
//            }
//        });
//    }


    private int getCurrentUserId() {
        return UserSession.getInstance().getUserId();
    }


}