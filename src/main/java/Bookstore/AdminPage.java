package Bookstore;

import Bookstore.scenes.admin.AdminDashboard;
import com.google.api.gax.paging.Page;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminPage extends Application {
	
	private Stage primaryStage;
	private Scene adminScene;
	
	public AdminPage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void loadScene() {
		AdminDashboard adminDash = new AdminDashboard();
		
		adminScene = new Scene(adminDash.getRoot(), 800, 850);
		
	}
	
	public void showAdminScene() {
		primaryStage.setScene(adminScene);
	}

	@Override
	public void start(Stage stage) throws Exception {
		AdminDashboard adminDash = new AdminDashboard("Inside start");
		adminScene = new Scene(adminDash.getRoot(), 800, 850);
		stage.setScene(adminScene);
		stage.setTitle("Admin Dashboard");
	}
}
