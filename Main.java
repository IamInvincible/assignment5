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

		Button makeAlgaeButton = new Button("makeAlgae");
		menu.add(makeAlgaeButton, 1, 0);
		
		Button makeCritter1Button = new Button("makeCritter1");
		menu.add(makeCritter1Button, 1, 1);
		
		Button makeCritter2Button = new Button("makeCritter2");
		menu.add(makeCritter2Button, 1, 2);
		
		Button makeCritter3Button = new Button("makeCritter3");
		menu.add(makeCritter3Button, 1, 3);
		
		Button makeCritter4Button = new Button("makeCritter4");
		menu.add(makeCritter4Button, 1, 4);

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

		makeAlgaeButton.setOnAction(new EventHandler<ActionEvent>() {
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
		
		makeCritter1Button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Critter.makeCritter("Critter1");
					Critter.displayWorld(borderPane);
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		makeCritter2Button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Critter.makeCritter("Critter2");
					Critter.displayWorld(borderPane);
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		makeCritter3Button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Critter.makeCritter("Critter3");
					Critter.displayWorld(borderPane);
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		makeCritter4Button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Critter.makeCritter("Critter4");
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
