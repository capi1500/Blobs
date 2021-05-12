package simulation.config;

import simulation.agents.blob.BlobFactory;
import simulation.agents.blob.Program;
import simulation.agents.blob.commands.*;
import simulation.agents.blob.components.BlobComponent;
import simulation.agents.blob.components.ReplicableComponent;
import simulation.agents.blob.mutations.Addition;
import simulation.agents.blob.mutations.Replacement;
import simulation.agents.blob.mutations.Substraction;
import simulation.agents.food.FoodFactory;
import simulation.agents.food.components.FoodComponent;
import simulation.agents.components.CircleGraphicComponent;
import simulation.interfaces.Loadable;
import utils.Pair;
import utils.vector.Vector2i;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Config implements Loadable{
	private static final Config config = new Config();
	
	private GraphicSettings graphic;
	private SimulationSettings simulation;
	
	// constructor
	
	private Config(){
	
	}
	
	// overrides
	
	@Override
	public void load(String path){
		graphic = new GraphicSettings();
		
		Command[] commands = new Command[5];
		commands[0] = new Move();
		commands[1] = new Scan();
		commands[2] = new ScanAndGo();
		commands[3] = new TurnLeft();
		commands[4] = new TurnRight();
		
		BlobFactory[] breeds = new BlobFactory[1];
		breeds[0] = new BlobFactory(
				new BlobComponent(
						new Program(
								new Scan(),
								new Move(),
								new ScanAndGo()
						),
						30,
						2),
				new CircleGraphicComponent(
						new Circle(graphic.getBlobRadius()),
						Color.BLUE
				),
				new ReplicableComponent(
						0.7f,
						10,
						0.2f,
						new Pair<>(new Addition(), 0.1f),
						new Pair<>(new Substraction(), 0.1f),
						new Pair<>(new Replacement(), 0.1f)
				)
		);
		FoodFactory[] foods = new FoodFactory[1];
		foods[0] = new FoodFactory(
				new FoodComponent(
						10,
						3
				),
				new CircleGraphicComponent(
						new Circle(graphic.getBlobRadius() + 2),
						Color.GREEN
				)
		);
		
		simulation = new SimulationSettings(
				new Vector2i(20, 20),
				0.3f,
				breeds,
				foods,
				commands
		);
	}
	
	// getters and setters
	
	public static Config get(){
		return config;
	}
	
	public static GraphicSettings getGraphicSettings(){
		return config.graphic;
	}
	
	public static SimulationSettings getSimulationSettings(){
		return config.simulation;
	}
}
