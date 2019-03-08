package com.solitaire;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationLoader extends Application {
		
	private static final int HEIGHT = 550;
	public static final int WIDTH = 1030;

	@Override
	public void start(Stage stage) throws Exception {		
		//create the first view page
		Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
		//the application title
		stage.setTitle("Group 5's JavaFx Baker's Dozen Solitaire");
		//sets a start default size of the scene
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		//stage.setOpacity(0.2);
		//sets a style for the application
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
