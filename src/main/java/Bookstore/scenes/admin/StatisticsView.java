package Bookstore.scenes.admin;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.AnchorPane;

public class StatisticsView {

    private AnchorPane rootPane;

    public StatisticsView() {
        initializeUI();
    }

    public AnchorPane getRoot() {
        return rootPane;
    }

    private void initializeUI() {

        rootPane = new AnchorPane();

        // Define x axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Condition");

        // Set x axis display information
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList(
                "Used Like New", "Moderately Used", "Heavily Used")));

        // Define y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cost");

        // Create BarChart
        BarChart<String, Number> statsChart = new BarChart<>(xAxis, yAxis);

        //AnchorPane.setTopAnchor(statsChart, 300.0);
        //AnchorPane.setLeftAnchor(statsChart, 50.0);
        //AnchorPane.setBottomAnchor(statsChart, 250.0);

        rootPane.getChildren().add(statsChart);
    }
}
