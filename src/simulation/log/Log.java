package simulation.log;

import ecs.Agent;
import simulation.SimulationEvent;
import simulation.agents.components.BlobComponent;
import listener.Listener;
import simulation.agents.components.FoodComponent;

public class Log implements Listener<SimulationEvent>{
	private int time;
	private int blobsAlive;
	private int foodFields;
	
	private int minimumProgramLength;
	private int maximumProgramLength;
	private int summedProgramLength;
	
	private float minimumEnergy;
	private float maximumEnergy;
	private float summedEnergy;
	
	private int minimumAge;
	private int maximumAge;
	private int summedAge;
	
	private String detailedLog;
	
	// methods
	
	public void reset(){
		blobsAlive = 0;
		foodFields = 0;
		minimumEnergy = minimumAge = minimumProgramLength = 0;
		maximumEnergy = maximumAge = maximumProgramLength = 0;
		summedEnergy = summedAge = summedProgramLength = 0;
		detailedLog = "";
	}
	
	private void addBlob(Agent agent){
		BlobComponent blob = agent.getComponent(BlobComponent.class);
		
		if(blobsAlive == 0){
			minimumEnergy = maximumEnergy = blob.getEnergy();
			minimumProgramLength = maximumProgramLength = blob.getProgram().size();
			minimumAge = maximumAge = blob.getAge();
		}
		
		minimumEnergy = Math.min(minimumEnergy, blob.getEnergy());
		summedEnergy += blob.getEnergy();
		maximumEnergy = Math.max(maximumEnergy, blob.getEnergy());
		
		minimumProgramLength = Math.min(minimumProgramLength, blob.getProgram().size());
		summedProgramLength += blob.getProgram().size();
		maximumProgramLength = Math.max(maximumProgramLength, blob.getProgram().size());
		
		minimumAge = Math.min(minimumAge, blob.getAge());
		summedAge += blob.getAge();
		maximumAge = Math.max(maximumAge, blob.getAge());
		blobsAlive++;
		
		detailedLog += agent.getLog() + "\n";
	}
	
	private void addFood(Agent agent){
		FoodComponent food = agent.getComponent(FoodComponent.class);
		if(!food.isHarvested())
			foodFields++;
	}
	
	public void detailedLog(){
		System.out.println("########################################");
		System.out.print(detailedLog);
		System.out.println("########################################");
	}
	
	public void log(){
		System.out.println(time + ", rob: " + blobsAlive + ", żyw: " + foodFields +
						   ", prg: " + minimumProgramLength + "/" + (float)(summedProgramLength) / blobsAlive + "/" + maximumProgramLength +
						   ", energ: " + minimumEnergy + "/" + summedEnergy / blobsAlive + "/" + maximumEnergy +
						   ", wiek: " + minimumAge + "/" + (float)(summedAge) / blobsAlive + "/" + maximumAge);
	}
	
	// constructor
	
	public Log(){
		reset();
	}
	
	// overrides
	
	@Override
	public void onSignal(SimulationEvent signal){
		if(signal.type == SimulationEvent.Type.LogAgent){
			if(signal.agent.isAlive()){
				if(signal.agent.hasComponent(BlobComponent.class))
					addBlob(signal.agent);
				else if(signal.agent.hasComponent(FoodComponent.class))
					addFood(signal.agent);
			}
		}
		else if(signal.type == SimulationEvent.Type.NextFrame)
			time++;
	}
}
