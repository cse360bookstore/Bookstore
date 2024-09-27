package Bookstore.scenes;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.datamodels.BookDAO;
import Bookstore.models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class BuyingProcess {
    DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
    private final BookDAO bookDAO = new BookDAO(dataSource);

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> categoryColumn;

    @FXML
    private TableColumn<Book, String> conditionColumn;

    @FXML
    private TableColumn<Book, Double> originalPriceColumn;

    @FXML
    private TableColumn<Book, Double> newPriceColumn;

    // This method will be called to initialize the view
    @FXML
    public void initialize() {
        // Set up the columns in the table
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        originalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));
        newPriceColumn.setCellValueFactory(new PropertyValueFactory<>("newPrice"));

        // Load the book data into the table
        try {
            loadBooks();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (display an error message, log it, etc.)
        }
    }

    private void loadBooks() throws SQLException {
        List<Book> bookList = bookDAO.getAllBooks();

        ObservableList<Book> bookObservableList = FXCollections.observableArrayList(bookList);

        bookTable.setItems(bookObservableList);
    }
}