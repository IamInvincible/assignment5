/* CRITTERS Main.java
 * EE422C Project 5 submission by
 * Jason Fang
 * jhf649
 * 16238
 * Cejay Zhu
 * cz4723
 * 16238
 * Slip days used: 0
 * Fall 2016
 */

package assignment5;

import assignment5.Critter;
import assignment5.InvalidCritterException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	Map<String, String> desiredStats = new HashMap<String, String>();
	ArrayList<String> createdStats = new ArrayList<String>();
	private static String myPackage;
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		BorderPane borderPane = new BorderPane();
		borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		final Text title = new Text("Welcome to Critter");
		title.setStyle("-fx-font: 24 arial;");
		GridPane titlePane = new GridPane();
		GridPane.setConstraints(title, 0, 0);
		titlePane.getChildren().add(title);
		titlePane.setAlignment(Pos.CENTER);
		borderPane.setTop(titlePane);

		//menu
		GridPane menu = new GridPane();
		menu.setPadding(new Insets(10, 10, 10, 10));
		menu.setVgap(5);
		menu.setHgap(5);
		
		Button displayWorldButton = new Button("Display World");
		GridPane.setConstraints(displayWorldButton, 0, 0);
		menu.getChildren().add(displayWorldButton);

		Button clearWorldButton = new Button("Clear World");
		GridPane.setConstraints(clearWorldButton, 0, 1);
		menu.getChildren().add(clearWorldButton);
		
		final TextField makeCritterInput = new TextField();
		makeCritterInput.setPromptText("Enter a Critter name.");
		GridPane.setConstraints(makeCritterInput, 0, 2);
		GridPane.setMargin(makeCritterInput, new Insets(15, 0, 0, 0));
		menu.getChildren().add(makeCritterInput);
		
		final TextField makeCritterNumber = new TextField();
		makeCritterNumber.setPromptText("#");
		makeCritterNumber.setPrefWidth(20);
		GridPane.setConstraints(makeCritterNumber, 1, 2);
		GridPane.setMargin(makeCritterNumber, new Insets(15, 0, 0, 0));
		menu.getChildren().add(makeCritterNumber);
		
		Button makeCritter = new Button("Create");
		GridPane.setConstraints(makeCritter, 2, 2);
		GridPane.setMargin(makeCritter, new Insets(15, 0, 0, 0));
		menu.getChildren().add(makeCritter);
		
		Text invalidCritterMessage = new Text("Invalid Critter");
		invalidCritterMessage.setFill(Color.WHITE);
		GridPane.setConstraints(invalidCritterMessage, 0, 3);
		menu.getChildren().add(invalidCritterMessage);
		
		final TextField runStatsInput = new TextField();
		runStatsInput.setPromptText("Enter a Critter name.");
		GridPane.setConstraints(runStatsInput, 0, 4);
		menu.getChildren().add(runStatsInput);
		
		Button runStats = new Button("Run Stats");
		GridPane.setConstraints(runStats, 1, 4);
		menu.getChildren().add(runStats);
		
		Text invalidStatsMessage = new Text("Invalid Critter");
		invalidStatsMessage.setFill(Color.WHITE);
		GridPane.setConstraints(invalidStatsMessage, 0, 5);
		menu.getChildren().add(invalidStatsMessage);
		
		Button runStatsAll = new Button("Run Stats for All");
		GridPane.setConstraints(runStatsAll, 0, 6);
		menu.getChildren().add(runStatsAll);
		
		Button worldTimeStep1 = new Button("1 World Time Step");
		GridPane.setConstraints(worldTimeStep1, 0, 7);
		GridPane.setMargin(worldTimeStep1, new Insets(15, 0, 0, 0));
		menu.getChildren().add(worldTimeStep1);
		
		Button worldTimeStep10 = new Button("10 World Time Steps");
		GridPane.setConstraints(worldTimeStep10, 0, 8);
		menu.getChildren().add(worldTimeStep10);
		
		Button worldTimeStep100 = new Button("100 World Time Steps");
		GridPane.setConstraints(worldTimeStep100, 0, 9);
		menu.getChildren().add(worldTimeStep100);
		
		Button worldTimeStep1000 = new Button("1000 World Time Steps");
		GridPane.setConstraints(worldTimeStep1000, 0, 10);
		menu.getChildren().add(worldTimeStep1000);
		
		final TextField seedNumber = new TextField();
		seedNumber.setPromptText("Enter a seed number.");
		GridPane.setConstraints(seedNumber, 0, 11);
		GridPane.setMargin(seedNumber, new Insets(15, 0, 0, 0));
		menu.getChildren().add(seedNumber);
		
		Button setSeed = new Button("Set Seed");
		GridPane.setConstraints(setSeed, 1, 11);
		GridPane.setMargin(setSeed, new Insets(15, 0, 0, 0));
		menu.getChildren().add(setSeed);
		
		Text currentSeed = new Text("");
		currentSeed.setFill(Color.GREEN);
		GridPane.setConstraints(currentSeed, 0, 12);
		menu.getChildren().add(currentSeed);
		
		Button quitButton = new Button("Quit");
		GridPane.setConstraints(quitButton, 0, 13);
		GridPane.setMargin(quitButton, new Insets(15, 0, 0, 0));
		menu.getChildren().add(quitButton);
		
		borderPane.setRight(menu);
		menu.setAlignment(Pos.CENTER);
		
		//Left Border
		GridPane leftBorder = new GridPane();

		borderPane.setLeft(leftBorder);
		leftBorder.setAlignment(Pos.CENTER);
		
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
		
		makeCritter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String inputCritter = makeCritterInput.getText();
				try{
					invalidCritterMessage.setFill(Color.WHITE);
					int numCritters = Integer.parseInt(makeCritterNumber.getText());
					for(int i = 0; i < numCritters; i++){
						Critter.makeCritter(inputCritter);
					}
					Critter.displayWorld(borderPane);
				}
				catch(InvalidCritterException e){
					invalidCritterMessage.setFill(Color.FIREBRICK);
				}
			}
		});
		
		runStats.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String inputCritter = runStatsInput.getText();
				try{
					String qualified_critter_class_name = myPackage + "." + inputCritter;
					Class<?> c = Class.forName(qualified_critter_class_name);
					boolean validCritter = Critter.class.isAssignableFrom(c);
					if(!validCritter){
						invalidStatsMessage.setFill(Color.FIREBRICK);
						return;
					}
					invalidStatsMessage.setFill(Color.WHITE);
					try {
						Method method = c.getMethod("runStats", String.class);
						Object o = method.invoke(null, inputCritter);
						desiredStats.put(inputCritter, (String)o);
						for(String e: desiredStats.keySet()){
							if(createdStats.contains(e)){
								for(javafx.scene.Node node: leftBorder.getChildren()){
									if(node instanceof Text){
										if(((Text) node).getText().split(":")[0].equals(e)){
											((Text) node).setText(desiredStats.get(e));
										}
									}
								}
							}
							else{
								createdStats.add(e);
								Text newStats = new Text(desiredStats.get(e));
								GridPane.setConstraints(newStats, 0, createdStats.size());
								leftBorder.getChildren().add(newStats);
							}
						}
					} catch (NoSuchMethodException e) {
						invalidStatsMessage.setFill(Color.FIREBRICK);
					} catch (SecurityException e) {
						invalidStatsMessage.setFill(Color.FIREBRICK);
					} catch (IllegalAccessException e) {
						invalidStatsMessage.setFill(Color.FIREBRICK);
					} catch (IllegalArgumentException e) {
						invalidStatsMessage.setFill(Color.FIREBRICK);
					} catch (InvocationTargetException e) {
						invalidStatsMessage.setFill(Color.FIREBRICK);
					}
				}
				catch(ClassNotFoundException e){
					invalidStatsMessage.setFill(Color.FIREBRICK);
				}
			}
		});
		
		runStatsAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				for(String created: createdStats){
					String inputCritter = created;
					try{
						String qualified_critter_class_name = myPackage + "." + inputCritter;
						Class<?> c = Class.forName(qualified_critter_class_name);
						boolean validCritter = Critter.class.isAssignableFrom(c);
						if(!validCritter){
							invalidStatsMessage.setFill(Color.FIREBRICK);
							return;
						}
						invalidStatsMessage.setFill(Color.WHITE);
						try {
							Method method = c.getMethod("runStats", String.class);
							Object o = method.invoke(null, inputCritter);
							desiredStats.put(inputCritter, (String)o);
							for(String e: desiredStats.keySet()){
								if(createdStats.contains(e)){
									for(javafx.scene.Node node: leftBorder.getChildren()){
										if(node instanceof Text){
											if(((Text) node).getText().split(":")[0].equals(e)){
												((Text) node).setText(desiredStats.get(e));
											}
										}
									}
								}
								else{
									createdStats.add(e);
									Text newStats = new Text(desiredStats.get(e));
									GridPane.setConstraints(newStats, 0, createdStats.size());
									leftBorder.getChildren().add(newStats);
								}
							}
						} catch (NoSuchMethodException e) {
							invalidStatsMessage.setFill(Color.FIREBRICK);
						} catch (SecurityException e) {
							invalidStatsMessage.setFill(Color.FIREBRICK);
						} catch (IllegalAccessException e) {
							invalidStatsMessage.setFill(Color.FIREBRICK);
						} catch (IllegalArgumentException e) {
							invalidStatsMessage.setFill(Color.FIREBRICK);
						} catch (InvocationTargetException e) {
							invalidStatsMessage.setFill(Color.FIREBRICK);
						}
					}
					catch(ClassNotFoundException e){
						invalidStatsMessage.setFill(Color.FIREBRICK);
					}			
				}
			}
		});
		
		worldTimeStep1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				Critter.worldTimeStep();
				Critter.displayWorld(borderPane);
			}
		});
		
		worldTimeStep10.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				for(int i = 0; i < 10; i++){
					Critter.worldTimeStep();
				}
				Critter.displayWorld(borderPane);
			}
		});
		
		worldTimeStep100.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				for(int i = 0; i < 100; i++){
					Critter.worldTimeStep();
				}
				Critter.displayWorld(borderPane);
			}
		});
		
		worldTimeStep1000.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				for(int i = 0; i < 1000; i++){
					Critter.worldTimeStep();
				}
				Critter.displayWorld(borderPane);
			}
		});
		
		setSeed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				Critter.setSeed(Long.parseLong(seedNumber.getText()));
				currentSeed.setText("Seed set to: " + seedNumber.getText());
			}
		});
		
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		primaryStage.setTitle("Critter");
		primaryStage.setScene(new Scene(borderPane, 1200, 600));
		primaryStage.show();
	}

}