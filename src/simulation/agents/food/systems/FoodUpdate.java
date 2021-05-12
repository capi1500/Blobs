package simulation.agents.food.systems;

import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;
import simulation.agents.food.components.FoodComponent;

public class FoodUpdate extends EngineSystem{
	public FoodUpdate(Engine engine){
		super(engine, FoodComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		FoodComponent food = agent.getComponent(FoodComponent.class);
		
		food.update();
	}
}
