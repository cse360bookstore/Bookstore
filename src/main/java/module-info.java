module com.example.sellingprocess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sellingprocess to javafx.fxml;
    exports com.example.sellingprocess;
}