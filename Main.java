package assignment5;

import assignment5.Critter;
import assignment5.InvalidCritterException;

import java.lang.reflect.Method;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	int counter = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// you can have panes within panes
		
		BorderPane borderPane = new BorderPane();
		Text title = new Text("Welcome to Critter");
		borderPane.setTop(title);

		//menu
		GridPane menu = new GridPane();
		Button displayWorldButton = new Button("displayWorld");
		menu.add(displayWorldButton, 0, 0);

		Button clearWorldButton = new Button("clearWorld");
		menu.add(clearWorldButton, 0, 1);

		Button makeCritterButton = new Button("makeCritter");
		menu.add(makeCritterButton, 1, 0);

		Button worldTimeStepButton = new Button("worldTimeStep");
		menu.add(worldTimeStepButton, 2, 0);

		Button worldTimeStepButton10 = new Button("worldTimeStep10");
		menu.add(worldTimeStepButton10, 2, 1);

		Button worldTimeStepButton50 = new Button("worldTimeStep50");
		menu.add(worldTimeStepButton50, 2, 2);

		Button worldTimeStepButton100 = new Button("worldTimeStep100");
		menu.add(worldTimeStepButton100, 2, 3);
		
		Button quitButton = new Button("quit");
		menu.add(quitButton, 5, 0);

		borderPane.setBottom(menu);
		
		//left border
		GridPane leftBorder = new GridPane();
		
		Text stats = new Text("Stats");
		leftBorder.add(stats, 0, 0);
		
		Text stats1 = new Text("Stats1");
		leftBorder.add(stats1, 0, 1);
		
		borderPane.setLeft(leftBorder);
		
		//this button is basically useless
		displayWorldButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Critter.displayWorld(borderPane);
			}
		});

		clearWorldButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Critter.clearWorld();
				Critter.displayWorld(borderPane);
			}
		});

		makeCritterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Critter.makeCritter("Algae");
					Critter.displayWorld(borderPane);
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
				}
			}
		});

		worldTimeStepButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Critter.worldTimeStep();
				Critter.displayWorld(borderPane);
			}
		});

		worldTimeStepButton10.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < 10; i++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(borderPane);
			}
		});

		worldTimeStepButton50.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < 50; i++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(borderPane);
			}
		});

		worldTimeStepButton100.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < 100; i++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(borderPane);
			}
		});
		
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		primaryStage.setTitle("Critter");
		primaryStage.setScene(new Scene(borderPane, 1500, 800));
		primaryStage.show();
	}

}
