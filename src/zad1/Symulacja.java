package zad1;

import zad1.loader.Loader;
import zad1.simulation.Simulation;
import zad1.simulation.config.Config;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import zad1.utils.Path;

public class Symulacja extends Application{
	@Override
	public void start(Stage primaryStage){
		Loader.addListener(Config.getSimulationSettings());
		Config.getSimulationSettings().initTxt();
		Loader.load(new Path(Config.getSimulationSettings().getParameters()));
		
		Simulation.simulate();
		
		Loader.removeAllListeners();
		
		Scene scene = new Scene(Config.getGraphicSettings().getFxGroup(), Config.getGraphicSettings().getWindowSize().x, Config.getGraphicSettings().getWindowSize().y);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Blobs aka Robki");
		
		primaryStage.show();
	}
	
	
	public static void main(String[] args){
		Config.getSimulationSettings().setBoard(args[0]);
		Config.getSimulationSettings().setParameters(args[1]);
		launch(args);
	}
}
