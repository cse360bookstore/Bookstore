package Bookstore.scenes.buyer;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.AlertHelper;
import Bookstore.dataManagers.BookManager;
import Bookstore.models.BookWithUser;
import Bookstore.models.UserSession;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class BuyingProcess {
    @FXML
    private VBox booksContainer;
    @FXML
    private Label cartItemCount;
    @FXML
    private Button checkoutButton;

    private BookManager bookManager;

    @FXML
    public void initialize() {
        DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
        bookManager = new BookManager(dataSource);

        loadBooks();
        checkoutButton.setOnAction((event -> handleCheckoutAction()));
    }

    private void loadBooks() {
        try {
            ObservableList<BookWithUser> bookList = bookManager.getAllBooksForSale();
            booksContainer.getChildren().clear();

            if (bookList.isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "No Books Found", "There are currently no books available for purchase.");
                return;
            }

            for (BookWithUser book : bookList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Bookstore/scenes/buyer/BookItem.fxml"));
                VBox bookItem = loader.load();
                BookItem controller = loader.getController();
                controller.setBook(book, bookManager, this);
                booksContainer.getChildren().add(bookItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, "UI Error", "Failed to load the book items.");
        }
    }

    public void updateCartItemCount() {
        cartItemCount.setText("Cart Items: " + String.valueOf(BookItem.getShoppingCart().size()));
    }

    private void handleCheckoutAction() {
        Set<Integer> cartItems = BookItem.getShoppingCart();

        // Commpute total price
        StringBuilder sb = new StringBuilder();
        sb.append("Cart summary:\n");
        double totalPrice = 0;
        for (int bookId : cartItems) {
            try {
                BookWithUser book = bookManager.getBookById(bookId);
                sb.append(book.getTitle());
                sb.append(" - $");
                sb.append(book.getPrice());
                sb.append("\n");
                totalPrice += book.getPrice();
            } catch (SQLException e) {
                e.printStackTrace();
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing your purchase.");
            }
        }
        sb.append("Total: $");
        sb.append(totalPrice);

        Alert confirmAlert = AlertHelper.createAlert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want to Checkout?", sb.toString());
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int userId = UserSession.getInstance().getUserId();
                for (int bookId : cartItems) {
                    try {
                        BookWithUser book = bookManager.getBookById(bookId); // Assuming this method exists
                        boolean success = bookManager.buyBook(book.getBookID(), userId, book.getPrice());
                        if (!success) {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, "Purchase Failed", "Failed to purchase book: " + book.getTitle());
                            continue;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        AlertHelper.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing your purchase.");
                    }
                }
            }
        });

        BookItem.getShoppingCart().clear();
        updateCartItemCount();
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Success!", "Cart purchase confirmed successfully.");
    }


    @FXML
    private void handleRefreshAction() {
        loadBooks();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Refresh Successful", "Book catalog has been refreshed");
    }

}