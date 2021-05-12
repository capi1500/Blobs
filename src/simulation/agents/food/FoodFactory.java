package simulation.agents.food;

import ecs.Agent;
import ecs.Engine;
import ecs.Factory;
import ecs.components.CircleGraphicComponent;
import simulation.agents.food.components.FoodComponent;
import utils.vector.Vector2i;

public class FoodFactory extends Factory{
	private final FoodComponent food;
	private final CircleGraphicComponent circle;
	
	// methods
	
	@Override
	public Agent spawn(Engine engine, Object... objects){
		if(objects.length != 1 || !Vector2i.class.isAssignableFrom(objects[0].getClass()))
			throw new UnsupportedOperationException("Wrong arguments for FoodFactory, should be [Vector2i]");
		
		Vector2i position = (Vector2i)objects[0];
		
		Agent agent = new Agent(engine);
		
		agent.addComponent(FoodComponent.class, food.copy());
		agent.addComponent(CircleGraphicComponent.class, circle.copy());
		
		agent.getComponent(FoodComponent.class).setPosition(position);
		
		return agent;
	}
	
	// constructor
	
	public FoodFactory(FoodComponent food, CircleGraphicComponent circle){
		this.food = food;
		this.circle = circle;
	}
}
