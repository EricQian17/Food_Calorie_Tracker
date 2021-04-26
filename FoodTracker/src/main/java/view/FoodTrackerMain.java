package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FoodTrackerMain extends Application {

	public static void main(String[] args) {
		Application.launch(args);

	}
	@Override
	public void start(Stage stage) throws Exception{
		
		Parent root = FXMLLoader.load(getClass().getResource("FoodTrackerView.fxml"));
		Scene scene = new Scene(root);
		
		stage.setTitle("Food Tracker");
		stage.setScene(scene);
		stage.show();
	}

}
