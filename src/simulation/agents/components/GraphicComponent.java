package simulation.agents.components;

import ecs.Action;
import ecs.actions.Kill;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import simulation.agents.actions.*;
import simulation.config.Config;
import javafx.scene.shape.Circle;
import utils.vector.Vector2i;

import java.util.LinkedList;
import java.util.Queue;

public class GraphicComponent extends SimpleComponent<Circle>{
	private interface ActionLambda{
		Animation execute();
	}
	
	private final SequentialTransition sequentialTransition = new SequentialTransition();
	private final Queue<ActionLambda> actions = new LinkedList<>();
	
	private double frameTime;
	
	// methods
	
	public void update(){
		if(!sequentialTransition.getStatus().equals(Animation.Status.STOPPED))
			sequentialTransition.stop();
		sequentialTransition.getChildren().clear();
		
		frameTime = Config.getGraphicSettings().getTickTime();
		
		while(!actions.isEmpty())
			sequentialTransition.getChildren().add(actions.poll().execute());
		
		if(!sequentialTransition.getChildren().isEmpty())
			sequentialTransition.play();
	}
	
	private Animation addMove(Vector2i from, Vector2i to, Duration duration){
		TranslateTransition move = new TranslateTransition(duration);
		
		int fieldSize = Config.getGraphicSettings().getFieldSize();
		
		move.setFromX(from.x * fieldSize);
		move.setFromY(from.y * fieldSize);
		move.setToX(to.x * fieldSize);
		move.setToY(to.y * fieldSize);
		
		double deltaX = Math.abs(move.getFromX() - move.getToX());
		double deltaY = Math.abs(move.getFromY() - move.getToY());
		if(deltaX > fieldSize || deltaY > fieldSize){
			move.setDuration(Duration.millis(duration.toMillis() / 2));
			
			TranslateTransition move2 = new TranslateTransition(Duration.millis(duration.toMillis() / 2));
			move2.setFromX(move.getFromX());
			move2.setFromY(move.getFromY());
			if(deltaX > fieldSize){
				if(to.x == 0){
					move.setFromX(-fieldSize);
					move2.setToX(Config.getSimulationSettings().getSize().x * fieldSize);
				}
				else{
					move.setFromX(Config.getSimulationSettings().getSize().x * fieldSize);
					move2.setToX(-fieldSize);
				}
			}
			if(deltaY > fieldSize){
				if(to.y == 0){
					move.setFromY(-fieldSize);
					move2.setToY(Config.getSimulationSettings().getSize().y * fieldSize);
				}
				else{
					move.setFromY(Config.getSimulationSettings().getSize().y * fieldSize);
					move2.setToY(-fieldSize);
				}
			}
			
			SequentialTransition sequentialTransition = new SequentialTransition();
			sequentialTransition.setCycleCount(1);
			sequentialTransition.getChildren().add(move2);
			sequentialTransition.getChildren().add(move);
			return sequentialTransition;
		}
		
		return move;
	}
	
	private Animation changeColor(Color from, Color to, Duration duration){
		FillTransition fillTransition = new FillTransition(duration);
		
		fillTransition.setFromValue(from);
		fillTransition.setToValue(to);
		
		return fillTransition;
	}
	
	private void init(){
		get().setVisible(false);
		get().setCenterX(Config.getGraphicSettings().getFieldSize() / 2);
		get().setCenterY(Config.getGraphicSettings().getFieldSize() / 2);
		
		sequentialTransition.setCycleCount(1);
		sequentialTransition.setNode(get());
	}
	
	// constructors
	
	public GraphicComponent(){
		init();
	}
	
	public GraphicComponent(Circle obj){
		super(obj);
		init();
	}
	
	private GraphicComponent(GraphicComponent copy){
		super(new Circle(copy.get().getCenterX(), copy.get().getCenterY(), copy.get().getRadius(), copy.get().getFill()));
		frameTime = copy.frameTime;
		init();
	}
	
	// overrides
	
	@Override
	public void onSignal(Action signal){
		if(signal.getClass() == Move.class){
			Move move = (Move)signal;
			actions.add(() -> addMove(move.getFrom(), move.getTo(), Duration.millis(frameTime)));
		}
		else if(signal.getClass() == ScanAndGo.class){
			ScanAndGo scanAndGo = (ScanAndGo)signal;
			if(!scanAndGo.getFrom().equals(scanAndGo.getTo()))
				actions.add(() -> {
					ParallelTransition parallelTransition = new ParallelTransition();
					SequentialTransition sequentialTransition = new SequentialTransition();
					
					sequentialTransition.getChildren().add(changeColor(Color.BLUE, Color.CYAN, Duration.millis(frameTime / 2)));
					sequentialTransition.getChildren().add(changeColor(Color.CYAN, Color.BLUE, Duration.millis(frameTime / 2)));
					
					parallelTransition.getChildren().add(addMove(scanAndGo.getFrom(), scanAndGo.getTo(), Duration.millis(frameTime)));
					parallelTransition.getChildren().add(sequentialTransition);
					
					return parallelTransition;
				});
		}
		else if(signal.getClass() == Scan.class){
			actions.add(() -> {
				SequentialTransition sequentialTransition = new SequentialTransition();
				sequentialTransition.getChildren().add(changeColor(Color.BLUE, Color.RED, Duration.millis(frameTime / 2)));
				sequentialTransition.getChildren().add(changeColor(Color.RED, Color.BLUE, Duration.millis(frameTime / 2)));
				return sequentialTransition;
			});
		}
		else if(signal.getClass() == TurnLeft.class || signal.getClass() == TurnRight.class){
			actions.add(() -> {
				SequentialTransition sequentialTransition = new SequentialTransition();
				sequentialTransition.getChildren().add(changeColor(Color.BLUE, Color.LIGHTGREEN, Duration.millis(frameTime / 2)));
				sequentialTransition.getChildren().add(changeColor(Color.LIGHTGREEN, Color.BLUE, Duration.millis(frameTime / 2)));
				return sequentialTransition;
			});
		}
		else if(signal.getClass() == FoodHarvested.class){
			actions.add(() -> {
				Animation animation = changeColor(Color.GREEN, Color.YELLOW, Duration.millis(frameTime / 2));
				animation.setDelay(Duration.millis(((FoodHarvested)signal).getTime() * frameTime));
				return animation;
			});
		}
		else if(signal.getClass() == FoodRegrown.class)
			actions.add(() -> changeColor(Color.YELLOW, Color.GREEN, Duration.millis(frameTime / 2)));
	}
	
	@Override
	public GraphicComponent copy(){
		return new GraphicComponent(this);
	}
	
	@Override
	public void onCreation(){
		Config.getGraphicSettings().getFxGroup().getChildren().add(get());
	}
	
	@Override
	public void onDestruction(){
		Config.getGraphicSettings().getFxGroup().getChildren().remove(get());
	}
	
	@Override
	public String getLog(){
		return "GraphicComponent=" + get().toString();
	}
	
}
