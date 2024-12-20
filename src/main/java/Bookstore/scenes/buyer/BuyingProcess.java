package Bookstore.scenes.buyer;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.AlertHelper;
import Bookstore.dataManagers.BookManager;
import Bookstore.models.BookWithUser;
import Bookstore.models.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import javafx.event.ActionEvent;

public class BuyingProcess {
    @FXML
    private VBox booksContainer;
    @FXML
    private Label cartItemCount;
    @FXML
    private Button checkoutButton;
    @FXML
    private CheckBox naturalScienceButton;
    @FXML
    private CheckBox computerScienceButton;
    @FXML
    private CheckBox englishLanguageButton;
    @FXML
    private CheckBox scienceButton;
    @FXML
    private CheckBox otherButton;

    @FXML
    private CheckBox usedLikeNewConditionButton;
    @FXML
    private CheckBox moderatelyUsedConditionButton;
    @FXML
    private CheckBox heavilyUsedConditionButton;
    @FXML
    private ComboBox<String> sortComboBox;

    private String selectedGenre = "All";
    private String selectedCondition = "All";
    private String sortBy = "Date Listed";
    private BookManager bookManager;


    @FXML
    public void initialize() {
        DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
        bookManager = new BookManager(dataSource);

        naturalScienceButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());
        computerScienceButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());
        englishLanguageButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());
        scienceButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());
        otherButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());

        usedLikeNewConditionButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());
        moderatelyUsedConditionButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());
        heavilyUsedConditionButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> loadBooks());

        sortComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> loadBooks());
        sortComboBox.getSelectionModel().selectFirst();

        loadBooks();
        checkoutButton.setOnAction((event -> handleCheckoutAction()));
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/scenes/login/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void loadBooks() {
        try {
            ObservableList<BookWithUser> bookList = bookManager.getAllBooksForSale();
            if (bookList.isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "No Books Found", "There are currently no books available for purchase. Try widening your filters.");
            }
            ObservableList<BookWithUser> filteredBooks = FXCollections.observableArrayList(bookList);

            filteredBooks = filteredBooks.filtered(book -> {
                if (naturalScienceButton.isSelected() && book.getCategory().equalsIgnoreCase("Natural Science")) {
                    return true;
                } else if (computerScienceButton.isSelected() && book.getCategory().equalsIgnoreCase("Computer Science")) {
                    return true;
                } else if (englishLanguageButton.isSelected() && book.getCategory().equalsIgnoreCase("English Language")) {
                    return true;
                } else if (scienceButton.isSelected() && book.getCategory().equalsIgnoreCase("Science")) {
                    return true;
                } else if (otherButton.isSelected() && book.getCategory().equalsIgnoreCase("Other")) {
                    return true;
                }
                return false;
            });
            filteredBooks = FXCollections.observableArrayList(filteredBooks);

            filteredBooks = filteredBooks.filtered(book -> {
                if (usedLikeNewConditionButton.isSelected() && book.getBookCondition().equalsIgnoreCase("Used Like New")) {
                    return true;
                } else if (moderatelyUsedConditionButton.isSelected() && book.getBookCondition().equalsIgnoreCase("Moderately Used")) {
                    return true;
                } else if (heavilyUsedConditionButton.isSelected() && book.getBookCondition().equalsIgnoreCase("Heavily Used")) {
                    return true;
                }
                return false;
            });

            filteredBooks = FXCollections.observableArrayList(filteredBooks);
            updateGenreCounts(filteredBooks);
            String selectedSort = sortComboBox.getSelectionModel().getSelectedItem();
            Comparator<BookWithUser> comparator = null;

            if ("Price (Low to High)".equals(selectedSort)) {
                comparator = Comparator.comparingDouble(BookWithUser::getPrice);
            } else if ("Price (High to Low)".equals(selectedSort)) {
                comparator = Comparator.comparingDouble(BookWithUser::getPrice).reversed();
            } else if ("Date Listed (Newest First)".equals(selectedSort)) {
                comparator = Comparator.comparing(BookWithUser::getListedAt).reversed();
            } else if ("Date Listed (Oldest First)".equals(selectedSort)) {
                comparator = Comparator.comparing(BookWithUser::getListedAt);
            } else if ("Title (A to Z)".equals(selectedSort)) {
                comparator = Comparator.comparing(BookWithUser::getTitle, String.CASE_INSENSITIVE_ORDER);
            } else if ("Title (Z to A)".equals(selectedSort)) {
                comparator = Comparator.comparing(BookWithUser::getTitle, String.CASE_INSENSITIVE_ORDER).reversed();
            }

            System.out.println(comparator);
            FXCollections.sort(filteredBooks, comparator);

            booksContainer.getChildren().clear();
            for (BookWithUser book : filteredBooks) {
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
        if (cartItems.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Empty Cart", "The cart is empty and therefore you cannot checkout.");
            return;
        }

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
        loadBooks();
    }


    private void updateGenreCounts(ObservableList<BookWithUser> bookList) {
        int natural_science_count = 0;
        int computer_science_count = 0;
        int english_language_count = 0;
        int science_count = 0;
        int other_count = 0;

        for (BookWithUser book : bookList) {
            String category = book.getCategory();
            if (category.equals("Natural Science")) {
                natural_science_count += 1;
            }
            else if (category.equals("Computer Science")) {
                computer_science_count += 1;
            }
            else if (category.equals("English Language")) {
                english_language_count += 1;
            }
            else if (category.equals("Science")) {
                science_count += 1;
            }
            else {
                other_count += 1;
            }
        }

        // Update the text of each checkbox to include the counts
        naturalScienceButton.setText("Natural Science (" + natural_science_count + ")");
        computerScienceButton.setText("Computer Science (" + computer_science_count + ")");
        englishLanguageButton.setText("English Language (" + english_language_count + ")");
        scienceButton.setText("Science (" + science_count + ")");
        otherButton.setText("Other (" + other_count + ")");
    }

    @FXML
    private void handleRefreshAction() {
        loadBooks();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Refresh Successful", "Book catalog has been refreshed");
    }

}