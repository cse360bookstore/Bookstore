package application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage primaryStage;
	private Scene adminScene;
	
	public SceneController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void loadScene() {
		AdminDashboard adminDash = new AdminDashboard(this);
		
		adminScene = new Scene(adminDash.getRoot(), 800, 850);
		
	}
	
	public void showAdminScene() {
		primaryStage.setScene(adminScene);
	}

}
