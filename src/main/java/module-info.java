module com.example.sellingprocess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.zaxxer.hikari;
    requires java.smartcardio;

    opens Bookstore to javafx.fxml;
    exports Bookstore;
    exports Bookstore.scenes to javafx.fxml;
    opens Bookstore.scenes to javafx.fxml;
    opens Bookstore.components to javafx.fxml;
    opens Bookstore.models to javafx.base;

    exports Bookstore.components to javafx.fxml;
}