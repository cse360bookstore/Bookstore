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
import javafx.scene.layout.VBox;

import javax.sql.DataSource;
import java.io.IOException;

public class BuyingProcess {
    @FXML
    private VBox booksContainer;
    private BookManager bookManager;

    @FXML
    public void initialize() {
        DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
        bookManager = new BookManager(dataSource);

        loadBooks();
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
                controller.setBook(book, bookManager);
                booksContainer.getChildren().add(bookItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, "UI Error", "Failed to load the book items.");
        }
    }


    @FXML
    private void handleRefreshAction() {
        loadBooks();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Refresh Successful", "Book catalog has been refreshed");
    }

}