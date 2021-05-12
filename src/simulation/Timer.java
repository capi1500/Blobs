package simulation;

public class Timer{
	private int time = 0;
	
	// methods
	
	public void update(){
		time++;
		Simulation.getSimulationEventEmitter().send(SimulationEvent.nextFrame());
	}
	
	// getters and setters
	
	public int getTime(){
		return time;
	}
}
