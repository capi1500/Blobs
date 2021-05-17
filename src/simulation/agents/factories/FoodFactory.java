package simulation.agents.factories;

import ecs.Agent;
import ecs.Engine;
import ecs.Factory;
import simulation.agents.components.GraphicComponent;
import simulation.agents.components.FoodComponent;
import simulation.agents.components.PositionComponent;
import simulation.config.Config;
import utils.vector.Vector2i;

public class FoodFactory extends Factory{
	private final FoodComponent food;
	private final GraphicComponent circle;
	
	// methods
	
	@Override
	public Agent spawn(Engine engine, Object... objects){
		if(objects.length != 1 || !Vector2i.class.isAssignableFrom(objects[0].getClass()))
			throw new UnsupportedOperationException("Wrong arguments for FoodFactory, should be [Vector2i]");
		
		Vector2i position = (Vector2i)objects[0];
		
		Agent agent = new Agent(engine);
		
		agent.addComponent(FoodComponent.class, food.copy());
		agent.addComponent(GraphicComponent.class, circle.copy());
		agent.addComponent(PositionComponent.class, new PositionComponent(position));
		
		agent.getComponent(GraphicComponent.class).get().setTranslateX(position.x * Config.getGraphicSettings().getFieldSize());
		agent.getComponent(GraphicComponent.class).get().setTranslateY(position.y * Config.getGraphicSettings().getFieldSize());
		
		return agent;
	}
	
	// constructor
	
	public FoodFactory(FoodComponent food, GraphicComponent circle){
		this.food = food;
		this.circle = circle;
	}
}
