package application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage primaryStage;
	private Scene adminScene;
	private Scene transactionScene;
	private Scene statisticsScene;
	
	public SceneController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void loadScene() {
		AdminDashboard adminDash = new AdminDashboard(this);
		TransactionView transactionDash = new TransactionView();
		StatisticsView statisticsDash = new StatisticsView();
	
		// Previous 800x850
		adminScene = new Scene(adminDash.getRoot(), 800, 1200);
		transactionScene = new Scene(transactionDash.getRoot(), 800, 1200);
		statisticsScene = new Scene(statisticsDash.getRoot(), 800, 1200);
		
	}
	
	public void showAdminScene() {
		primaryStage.setScene(adminScene);
	}
	
	public void showTransactionScene() {
		primaryStage.setScene(transactionScene);
	}
	
	public void showStatisticsScene() {
		primaryStage.setScene(statisticsScene);
	}

}
