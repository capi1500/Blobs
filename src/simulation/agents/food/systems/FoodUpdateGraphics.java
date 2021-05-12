package simulation.agents.food.systems;

import simulation.config.Config;
import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;
import ecs.components.CircleGraphicComponent;
import simulation.agents.food.components.FoodComponent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class FoodUpdateGraphics extends EngineSystem{
	public FoodUpdateGraphics(Engine engine){
		super(engine, FoodComponent.class, CircleGraphicComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		FoodComponent food = agent.getComponent(FoodComponent.class);
		CircleGraphicComponent graphics = agent.getComponent(CircleGraphicComponent.class);
		
		if(food.isHarvested())
			graphics.setColor(Color.YELLOW);
		else
			graphics.setColor(Color.GREEN);
		
		graphics.getCircle().setVisible(agent.isAlive());
		graphics.getCircle().setCenterX(food.getPosition().x * Config.getGraphicSettings().getFieldSize());
		graphics.getCircle().setCenterY(food.getPosition().y * Config.getGraphicSettings().getFieldSize());
		
		if(food.isUpdateGraphics()){
			Paint paint = graphics.getColor();
			if(food.isHarvested()){
				Timeline colorChange = new Timeline(new KeyFrame(Duration.millis(food.getChangeTime()), event -> { }));
				colorChange.setCycleCount(1);
				colorChange.setOnFinished(tmp -> {
					graphics.getCircle().setFill(paint);
				});
				colorChange.play();
			}
			else
				graphics.getCircle().setFill(paint);
			food.setUpdateGraphics(false);
		}
	}
}
