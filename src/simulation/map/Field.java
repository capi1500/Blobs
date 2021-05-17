package simulation.map;

import ecs.Agent;
import simulation.agents.components.FoodComponent;

public class Field{
	private Agent food;
	
	// constructors
	
	public Field(Agent food){
		this.food = food;
	}
	
	public Field(){
	}
	
	// getters and setters
	
	public boolean hasFoodToHarvest(){
		return hasFood() && !food.getComponent(FoodComponent.class).isHarvested();
	}
	
	public boolean hasFood(){
		return food != null && food.hasComponent(FoodComponent.class);
	}
	
	public Agent getFood(){
		return food;
	}
}
