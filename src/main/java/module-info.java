module com.example.sellingprocess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.zaxxer.hikari;
    requires java.smartcardio;
    requires gax;
    requires org.jnrproject.posix;

    opens Bookstore to javafx.fxml;
    exports Bookstore;
    exports Bookstore.scenes to javafx.fxml;
    opens Bookstore.scenes to javafx.fxml;
    opens Bookstore.components to javafx.fxml;
    opens Bookstore.models to javafx.base;
    opens Bookstore.scenes.admin to javafx.base;

    exports Bookstore.components to javafx.fxml;
    exports Bookstore.scenes.admin to javafx.fxml;
    exports Bookstore.scenes.seller to javafx.fxml;
    opens Bookstore.scenes.seller to javafx.fxml;
    exports Bookstore.scenes.buyer to javafx.fxml;
    opens Bookstore.scenes.buyer to javafx.fxml;

}