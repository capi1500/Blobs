package simulation.agents.components;

import ecs.Action;
import ecs.Component;
import simulation.Simulation;
import simulation.SimulationEvent;
import simulation.agents.actions.FoodHarvested;
import simulation.agents.actions.FoodRegrown;

public class FoodComponent extends Component{
	private final int energy;
	private final int growthTime;
	
	private boolean harvested;
	private int deltaTime;
	private int turnTimeHarvested;
	
	// constructors
	
	public FoodComponent(int energy, int growthTime){
		this.harvested = false;
		this.deltaTime = 0;
		this.energy = energy;
		this.growthTime = growthTime;
	}
	
	private FoodComponent(FoodComponent copy){
		this.harvested = copy.harvested;
		this.deltaTime = copy.deltaTime;
		this.energy = copy.energy;
		this.growthTime = copy.growthTime;
	}
	
	// overrides
	
	@Override
	public void onSignal(Action signal){
		if(signal.getClass() == FoodHarvested.class){
			turnTimeHarvested = ((FoodHarvested)signal).getTime();
			harvested = true;
			deltaTime = 0;
			Simulation.getSimulationEventEmitter().send(SimulationEvent.foodHarvested());
		}
		else if(signal.getClass() == FoodRegrown.class){
			harvested = false;
			Simulation.getSimulationEventEmitter().send(SimulationEvent.foodRegrown());
		}
	}
	
	@Override
	public FoodComponent copy(){
		return new FoodComponent(this);
	}
	
	@Override
	public String getLog(){
		return "FoodComponent{\n\t\t\tenergy=" + energy +
					   		 "\n\t\t\tgrowthTime=" + growthTime +
					   		 "\n\t\t\tharvested=" + harvested +
					   		 "\n\t\t}";
	}
	
	// getters and setters
	
	public int getEnergy(){
		return energy;
	}
	
	public int getGrowthTime(){
		return growthTime;
	}
	
	public boolean isHarvested(){
		return harvested;
	}
	
	public void setHarvested(boolean harvested){
		this.harvested = harvested;
	}
	
	public int getDeltaTime(){
		return deltaTime;
	}
	
	public void setDeltaTime(int deltaTime){
		this.deltaTime = deltaTime;
	}
	
	public int getTurnTimeHarvested(){
		return turnTimeHarvested;
	}
}
