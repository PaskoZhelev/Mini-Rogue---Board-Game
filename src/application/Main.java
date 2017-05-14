package application;
	
import java.io.IOException;

import application.handlers.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));	      
	        primaryStage.setTitle(Constants.WINDOW_GAME_NAME);
	        primaryStage.setScene(new Scene(root));
	        primaryStage.setResizable(false);
	        primaryStage.sizeToScene();
	        primaryStage.getIcons().add(
	                new Image("/images/icon.png"));
	        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
