package Bookstore.scenes.admin;

import java.util.Arrays;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.dataManagers.AdminManager;
import Bookstore.models.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javax.sql.DataSource;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class StatisticsView {

    private AnchorPane rootPane;
    private BarChart<String, Number> statsChart;
    private AdminManager adminManager;

    public StatisticsView() {
        initializeUI();
    }

    public AnchorPane getRoot() {
        return rootPane;
    }

    private void initializeUI() {

        rootPane = new AnchorPane();
        
        DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
        adminManager = new AdminManager(dataSource);

        // Define x axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Condition");

        // Define y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Sales");
       
        // allow auto-range so axis can grow as purchases increase
        yAxis.setAutoRanging(true);
        
        // only show whole numbers 
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setForceZeroInRange(true);
        
        // Set x axis display information
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList(
                "Used Like New", "Moderately Used", "Heavily Used")));


        // Create BarChart
        statsChart = new BarChart<>(xAxis, yAxis);
        statsChart.setTitle("Sales by Book Condition");
        statsChart.setLegendVisible(false);
        
        // load data into the chart 
        try {
        	loadChartData();
        }catch (SQLException e) {
        	e.printStackTrace();
        }
        


        rootPane.getChildren().add(statsChart);
    }
    
    private void loadChartData() throws SQLException{
    	ObservableList<Transaction> conditionSales = adminManager.getSalesbyCondition();
    	
    	XYChart.Series<String, Number> series = new XYChart.Series<>();
    	series.setName("Sales");
    	
    	for (Transaction transaction : conditionSales) {
    		String condition = transaction.getCategory();
    		int count = transaction.getCount();
    		
    		// create data point 
    		XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(condition, count);
    		
    		// apply color based on the book condition
    		dataPoint.nodeProperty().addListener((observable, oldNode, newNode) ->{
    			if (newNode != null) {
    				switch (condition) {
						case "Used Like New":
							newNode.setStyle("-fx-bar-fill: Maroon");
							break;
						case "Moderately Used":
							newNode.setStyle("-fx-bar-fill: #CCCC00");
							break;
						case "Heavily Used":
							newNode.setStyle("-fx-bar-fill: Gray");
							break;
    				}
    			}
    			
    		});
    		series.getData().add(dataPoint);
    	}
    	
    	statsChart.getData().clear();
    	statsChart.getData().add(series);
    }
}
