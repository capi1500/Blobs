package simulation.agents.systems;

import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;
import simulation.Simulation;
import simulation.SimulationEvent;
import simulation.agents.actions.FoodHarvested;
import simulation.agents.actions.FoodRegrown;
import simulation.agents.actions.Move;
import simulation.agents.components.FoodComponent;

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
