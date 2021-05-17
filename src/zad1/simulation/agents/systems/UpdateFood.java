package zad1.simulation.agents.systems;

import zad1.ecs.Agent;
import zad1.ecs.Engine;
import zad1.ecs.EngineSystem;
import zad1.simulation.agents.actions.FoodRegrown;
import zad1.simulation.agents.components.FoodComponent;

public class UpdateFood extends EngineSystem{
	public UpdateFood(Engine engine){
		super(engine, FoodComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		FoodComponent food = agent.getComponent(FoodComponent.class);
		
		if(food.isHarvested()){
			if(food.getDeltaTime() == food.getGrowthTime()){
				agent.getActionEmitter().sendNow(new FoodRegrown());
			}
			else{
				food.setDeltaTime(food.getDeltaTime() + 1);
			}
		}
	}
}
