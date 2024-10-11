package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{

	@Override
	public void start(Stage primaryStage){
		SceneController sceneController = new SceneController(primaryStage);
		//sceneController.loadScene(); 
		sceneController.loadScene();
		sceneController.showAdminScene();
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
