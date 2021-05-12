package simulation;

import simulation.config.Config;
import simulation.log.Log;
import simulation.log.LogUpdateSystem;
import simulation.agents.blob.components.*;
import ecs.Agent;
import ecs.Engine;
import simulation.agents.blob.systems.UpdateBlob;
import simulation.agents.blob.systems.ReplicateBlob;
import ecs.components.CircleGraphicComponent;
import simulation.agents.food.components.FoodComponent;
import simulation.agents.food.systems.FoodUpdate;
import simulation.agents.food.systems.FoodUpdateGraphics;
import simulation.listener.Emitter;
import simulation.map.Board;
import simulation.map.Field;
import utils.Random;
import utils.vector.Vector2i;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulation{
	static private final Emitter<SimulationEvent> simulationEventEmitter = new Emitter<>();
	
	// methods
	
	static public void simulate(int time, int blobCount, int logTime){
		Engine engine = new Engine(
				BlobComponent.class,
				CircleGraphicComponent.class,
				ReplicableComponent.class,
				FoodComponent.class
		);
		
		engine.addSystem(new FoodUpdate(engine));
		engine.addSystem(new UpdateBlob(engine));
		engine.addSystem(new ReplicateBlob(engine));
		engine.addSystem(new FoodUpdateGraphics(engine));
		engine.addSystem(new LogUpdateSystem(engine));
		
		Board board = new Board(Config.getSimulationSettings().getSize());
		Timer timer = new Timer();
		Log log = new Log();
		simulationEventEmitter.addListener(log);
		
		for(int x = 0; x < board.getSize().x; x++){
			for(int y = 0; y < board.getSize().y; y++){
				if(Random.getRandomNumberGenerator().nextFloat() <= Config.getSimulationSettings().getFoodChance()){
					Agent food = Config.getSimulationSettings().getRandomFood().spawn(engine, new Vector2i(x, y));
					board.setField(new Vector2i(x, y), new Field(food));
					engine.addAgent(food);
				}
			}
		}
		
		for(int i = 0; i < blobCount; i++){
			int x = Random.getRandomNumberGenerator().nextInt(board.getSize().x);
			int y = Random.getRandomNumberGenerator().nextInt(board.getSize().y);
			Agent blob = Config.getSimulationSettings().getRandomBreed().spawn(engine, board, new Vector2i(x, y));
			engine.addAgent(blob);
			simulationEventEmitter.send(SimulationEvent.logAddBlob(blob));
		}
		
		simulationEventEmitter.processSignals();
		log.detailedLog();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Config.getGraphicSettings().getFrameTime()), event -> {
			engine.update();
			simulationEventEmitter.processSignals();
			timer.update();
			
			log.log();
			log.reset();
			if(timer.getTime() % logTime == 0)
				log.detailedLog();
		}));
		timeline.setCycleCount(time);
		timeline.setOnFinished(tmp -> {
			log.detailedLog();
			engine.onDestruction();
			simulationEventEmitter.onDestruction();
		});
		timeline.play();
	}
	
	// getters and setters
	
	public static Emitter<SimulationEvent> getSimulationEventEmitter(){
		return simulationEventEmitter;
	}
}
