package Bookstore.scenes.seller;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.dataManagers.BookManager;
import Bookstore.models.BookForSale;
import Bookstore.models.BookWithUser;
import Bookstore.models.UserSession;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SellerInventory implements Initializable {

    @FXML
    private TableView<BookForSale> bookTableView;

    @FXML
    private TableColumn<BookForSale, String> titleColumn;

    @FXML
    private TableColumn<BookForSale, String> authorColumn;

    @FXML
    private TableColumn<BookForSale, String> categoryColumn;

    @FXML
    private TableColumn<BookForSale, String> conditionColumn;

    @FXML
    private TableColumn<BookForSale, Double> priceColumn;

    @FXML
    private TableColumn<BookForSale, String> listedAtColumn;

    @FXML
    private TableColumn<BookForSale, Integer> publishYearColumn;

    @FXML
    private TableColumn<BookForSale, BookForSale.Status> statusColumn;




    private final DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
    private final BookManager bookManager = new BookManager(dataSource);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("bookCondition"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        listedAtColumn.setCellValueFactory(new PropertyValueFactory<>("listedAt"));
        publishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            loadBooksForSeller();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void loadBooksForSeller() throws SQLException {
        int sellerID = UserSession.getInstance().getUserId();
        ObservableList<BookForSale> books = bookManager.getBooksBySellerID(sellerID);
        bookTableView.setItems(books);
    }
}