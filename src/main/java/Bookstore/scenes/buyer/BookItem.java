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
    @FXML
    private Label genreLabel;
    @FXML
    private Label conditionLabel;

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
        genreLabel.setText("Genre: " + book.getCategory());
        conditionLabel.setText("Condition: " + book.getBookCondition());

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

    private int getCurrentUserId() {
        return UserSession.getInstance().getUserId();
    }

}