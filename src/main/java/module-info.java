module com.example.sellingprocess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.zaxxer.hikari;

    opens com.example.sellingprocess to javafx.fxml;
    exports com.example.sellingprocess;
}