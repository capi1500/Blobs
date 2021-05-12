package simulation.log;

import simulation.SimulationEvent;
import simulation.agents.blob.components.BlobComponent;
import simulation.listener.Listener;

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
	
	private boolean toReset;
	
	private String detailedLog;
	
	// methods
	
	public void reset(){
		toReset = true;
	}
	
	private void resetInternal(){
		toReset = false;
		minimumEnergy = minimumAge = minimumProgramLength = 0;
	    maximumEnergy = maximumAge = maximumProgramLength = 0;
	    summedEnergy = summedAge = summedProgramLength = 0;
	    detailedLog = "";
	}
	
	public void detailedLog(){
		System.out.println("########################################");
		System.out.print(detailedLog);
		System.out.println("########################################");
	}
	
	public void log(){
		System.out.println(time + ", rob: " + blobsAlive + ", żyw: " + foodFields +
						   ", prg: " + minimumProgramLength + "/" + (float)(summedProgramLength) / blobsAlive + "/" + maximumProgramLength +
						   ", energ: " + minimumEnergy + "/" + (float)(summedEnergy) / blobsAlive + "/" + maximumEnergy +
						   ", wiek: " + minimumAge + "/" + (float)(summedAge) / blobsAlive + "/" + maximumAge);
	}
	
	// constructor
	
	public Log(){
		reset();
	}
	
	// overrides
	
	@Override
	public void onSignal(SimulationEvent signal){
		if(signal.type == SimulationEvent.Type.NextFrame){
			time++;
		}
		else if(signal.type == SimulationEvent.Type.BlobDied){
			blobsAlive--;
		}
		else if(signal.type == SimulationEvent.Type.BlobSpawned){
			blobsAlive++;
		}
		else if(signal.type == SimulationEvent.Type.FoodHarvested){
			foodFields--;
		}
		else if(signal.type == SimulationEvent.Type.FoodRegrown){
			foodFields++;
		}
		else if(signal.type == SimulationEvent.Type.FoodSpawned){
			foodFields++;
		}
		else if(signal.type == SimulationEvent.Type.LogAddBlob){
			if(signal.agent.agent.isAlive()){
				BlobComponent blob = signal.agent.agent.getComponent(BlobComponent.class);
				if(toReset){
					resetInternal();
					minimumEnergy = summedEnergy = maximumEnergy = blob.getEnergy();
					minimumProgramLength = summedProgramLength = maximumProgramLength = blob.getProgram().size();
					minimumAge = summedAge = maximumAge = blob.getAge();
				}
				else{
					minimumEnergy = Math.min(minimumEnergy, blob.getEnergy());
					summedEnergy += blob.getEnergy();
					maximumEnergy = Math.max(maximumEnergy, blob.getEnergy());
					
					minimumProgramLength = Math.min(minimumProgramLength, blob.getProgram().size());
					summedProgramLength += blob.getProgram().size();
					maximumProgramLength = Math.max(maximumProgramLength, blob.getProgram().size());
					
					minimumAge = Math.min(minimumAge, blob.getAge());
					summedAge += blob.getAge();
					maximumAge = Math.max(maximumAge, blob.getAge());
				}
				detailedLog += signal.agent.agent.getLog() + "\n";
			}
		}
	}
}
