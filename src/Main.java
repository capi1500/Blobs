import simulation.Simulation;
import simulation.config.Config;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage){
		Config.get().load("");
		Simulation.simulate(1000, 50, 25);
		
		Scene scene = new Scene(Config.getGraphicSettings().getFxGroup(), 700, 700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Blobs aka Robki");
		
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
