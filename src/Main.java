import loader.Loader;
import simulation.Simulation;
import simulation.config.Config;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import utils.Path;

public class Main extends Application{
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
