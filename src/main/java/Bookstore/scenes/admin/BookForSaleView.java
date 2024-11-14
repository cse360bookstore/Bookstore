package Bookstore.scenes.admin;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.AlertHelper;
import Bookstore.dataManagers.AdminManager;
import Bookstore.models.BookForSale;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BookForSaleView {
    public BookForSaleView() throws SQLException {
        initializeUI();
    }
    public AnchorPane getRoot() {
        return rootPane;
    }
    private AnchorPane rootPane;
    DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
    private final AdminManager adminManager = new AdminManager(dataSource);
    private void initializeUI() throws SQLException {
        rootPane = new AnchorPane();

        TableView<BookForSale> dataTable = new TableView<>();

        // Title Column
        TableColumn<BookForSale, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Category Column
        TableColumn<BookForSale, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setMinWidth(150);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        // Condition Column
        TableColumn<BookForSale, String> conditionColumn = new TableColumn<>("Condition");
        conditionColumn.setMinWidth(100);
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("bookCondition"));

        // Author Column
        TableColumn<BookForSale, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setMinWidth(150);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        // Price Column
        TableColumn<BookForSale, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Listed Date Column
        TableColumn<BookForSale, LocalDateTime> listedDateColumn = new TableColumn<>("Listed At");
        listedDateColumn.setMinWidth(150);
        listedDateColumn.setCellValueFactory(new PropertyValueFactory<>("listedAt"));

        // Status Column
        TableColumn<BookForSale, BookForSale.Status> statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Update Button Column
        TableColumn<BookForSale, Void> updateButtonColumn = new TableColumn<>("Update");
        updateButtonColumn.setMinWidth(100);
        TableColumn<BookForSale, Void> updateColumn = new TableColumn<>("Action");

        Callback<TableColumn<BookForSale, Void>, TableCell<BookForSale, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Update");

            {
                btn.setOnAction(event -> {
                    BookForSale book = getTableView().getItems().get(getIndex());
                    showUpdateForm(book); // Launch update form for this book
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };

        updateColumn.setCellFactory(cellFactory);
        updateButtonColumn.setCellFactory((javafx.util.Callback<TableColumn<BookForSale, Void>, TableCell<BookForSale, Void>>) cellFactory);

        dataTable.getColumns().addAll(
                titleColumn,
                categoryColumn,
                conditionColumn,
                authorColumn,
                priceColumn,
                listedDateColumn,
                statusColumn,
                updateButtonColumn
        );

        ObservableList<BookForSale> booksForSale = adminManager.getAllBooksForSale();
        dataTable.setItems(booksForSale);

        rootPane.getChildren().add(dataTable);
    }

    private void showUpdateForm(BookForSale book) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Book");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField titleField = new TextField(book.getTitle());
        titleField.setPromptText("Title");

        TextField categoryField = new TextField(book.getCategory());
        categoryField.setPromptText("Category");

        TextField conditionField = new TextField(book.getBookCondition());
        conditionField.setPromptText("Condition");

        TextField authorField = new TextField(book.getAuthor());
        authorField.setPromptText("Author");

        TextArea descriptionArea = new TextArea(book.getDescription());
        descriptionArea.setPromptText("Description");

        TextField priceField = new TextField(String.valueOf(book.getPrice()));
        priceField.setPromptText("Price");

        Button submitButton = new Button("Update");
        submitButton.setOnAction(e -> {
            book.setTitle(titleField.getText());
            book.setCategory(categoryField.getText());
            book.setBookCondition(conditionField.getText());
            book.setAuthor(authorField.getText());
            book.setDescription(descriptionArea.getText());
            book.setPrice(Double.parseDouble(priceField.getText()));
            System.out.println("Status of book in submit" + book.getStatus());

            try {
                adminManager.updateBook(book);
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Success", "Book updated successfully");
            } catch (SQLException ex) {
                ex.printStackTrace();
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", "Failed to Update book" + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Category:"), categoryField,
                new Label("Condition:"), conditionField,
                new Label("Author:"), authorField,
                new Label("Description:"), descriptionArea,
                new Label("Price:"), priceField,
                submitButton
        );

        Scene scene = new Scene(vbox, 400, 400);
        updateStage.setScene(scene);
        updateStage.show();
    }


    private void updateTransaction(BookForSale book) throws SQLException {
        adminManager.updateBook(book);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Book updated successfully!", "continue");
    }
}
