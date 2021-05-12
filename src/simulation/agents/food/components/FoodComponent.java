package simulation.agents.food.components;

import simulation.Simulation;
import simulation.SimulationEvent;
import ecs.Component;
import utils.vector.Vector2i;

public class FoodComponent extends Component{
	private final int energy;
	private final int growthTime;
	private Vector2i position;
	
	private boolean harvested;
	private int deltaTime;
	private double changeTime;
	private boolean updateGraphics;
	
	// methods
	
	public void harvest(double changeTime){
		harvested = true;
		this.changeTime = changeTime;
		updateGraphics = true;
		deltaTime = 0;
		Simulation.getSimulationEventEmitter().send(SimulationEvent.foodHarvested(getAgent()));
	}
	
	public void update(){
		if(harvested){
			if(deltaTime == growthTime){
				harvested = false;
				updateGraphics = true;
				Simulation.getSimulationEventEmitter().send(SimulationEvent.foodRegrown(getAgent()));
			}
			else{
				deltaTime++;
			}
		}
	}
	// constructors
	
	public FoodComponent(int energy, int growthTime){
		this.harvested = false;
		this.deltaTime = 0;
		this.energy = energy;
		this.growthTime = growthTime;
		this.position = new Vector2i();
		this.updateGraphics = false;
	}
	
	private FoodComponent(FoodComponent copy){
		this.harvested = copy.harvested;
		this.deltaTime = copy.deltaTime;
		this.energy = copy.energy;
		this.growthTime = copy.growthTime;
		this.changeTime = copy.changeTime;
		if(copy.position != null)
			this.position = copy.position.copy();
		else
			this.position = new Vector2i();
		this.updateGraphics = copy.updateGraphics;
	}
	
	// overrides
	
	@Override
	public FoodComponent copy(){
		return new FoodComponent(this);
	}
	
	@Override
	public void onCreation(){
		Simulation.getSimulationEventEmitter().send(SimulationEvent.foodSpawned(getAgent()));
	}
	
	@Override
	public void onDestruction(){
	
	}
	
	@Override
	public String getLog(){
		return "FoodComponent{\n\t\t\tenergy=" + energy +
					   		 "\n\t\t\tgrowthTime=" + growthTime +
					   		 "\n\t\t\tposition=" + position +
					   		 "\n\t\t\tharvested=" + harvested +
					   		 "\n\t\t}";
	}
	// getters and setters
	
	public int getEnergy(){
		return energy;
	}
	
	public boolean isHarvested(){
		return harvested;
	}
	
	public Vector2i getPosition(){
		return position;
	}
	
	public void setPosition(Vector2i position){
		this.position = position;
	}
	
	public double getChangeTime(){
		return changeTime;
	}
	
	public boolean isUpdateGraphics(){
		return updateGraphics;
	}
	
	public void setUpdateGraphics(boolean updateGraphics){
		this.updateGraphics = updateGraphics;
	}
}
