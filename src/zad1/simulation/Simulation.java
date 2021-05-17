package zad1.simulation;

import zad1.loader.Loader;
import zad1.simulation.agents.components.*;
import zad1.simulation.agents.systems.*;
import zad1.simulation.config.Config;
import zad1.simulation.log.Log;
import zad1.simulation.log.LogUpdateSystem;
import zad1.ecs.Agent;
import zad1.ecs.Engine;
import zad1.listener.Emitter;
import zad1.simulation.map.Board;
import zad1.utils.Path;
import zad1.utils.Random;
import zad1.utils.vector.Vector2i;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulation{
	static private final Emitter<SimulationEvent> simulationEventEmitter = new Emitter<>();
	
	// methods
	
	static public void simulate(){
		Timer timer = new Timer();
		Log log = new Log();
		simulationEventEmitter.addListener(log);
		simulationEventEmitter.addListener(Config.getGraphicSettings());
		
		Engine engine = new Engine(
				BlobComponent.class,
				DirectionComponent.class,
				FoodComponent.class,
				GraphicComponent.class,
				PositionComponent.class,
				ReplicableComponent.class,
				ColorComponent.class
		);
		
		engine.addSystem(new UpdateFood(engine));
		engine.addSystem(new UpdateBlob(engine));
		engine.addSystem(new ReplicateBlob(engine));
		engine.addSystem(new UpdateGraphics(engine));
		engine.addSystem(new LogUpdateSystem(engine));
		
		Board board = new Board(engine);
		Loader.addListener(board);
		Loader.loadBoard(new Path(Config.getSimulationSettings().getBoard()));
		
		for(int i = 0; i < Config.getSimulationSettings().getStartingBlobCount(); i++){
			int x = Random.getRandomNumberGenerator().nextInt(board.getSize().x);
			int y = Random.getRandomNumberGenerator().nextInt(board.getSize().y);
			Agent blob = Config.getSimulationSettings().getRandomBreed().spawn(engine, board, new Vector2i(x, y));
			engine.addAgent(blob);
			simulationEventEmitter.send(SimulationEvent.logAgent(blob));
		}
		
		simulationEventEmitter.processSignals();
		
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.ZERO, event -> {
					log.reset();
					
					engine.update();
					
					Config.getGraphicSettings().resetTickTime();
					
					timer.update();
					simulationEventEmitter.processSignals();
					
					log.log();
					if(timer.getTime() % Config.getSimulationSettings().getLogTime() == 0)
						log.detailedLog();
				}),
				new KeyFrame(Duration.millis(Config.getGraphicSettings().getFrameTime()))
		);
		
		timeline.setCycleCount(Config.getSimulationSettings().getSimulationTime());
		timeline.setOnFinished(tmp -> {
			log.detailedLog();
			engine.onDestruction();
			simulationEventEmitter.onDestruction();
		});
		
		log.detailedLog();
		timeline.play();
	}
	
	// getters and setters
	
	public static Emitter<SimulationEvent> getSimulationEventEmitter(){
		return simulationEventEmitter;
	}
}
