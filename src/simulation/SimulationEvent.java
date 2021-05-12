package simulation;

import ecs.Agent;

public class SimulationEvent{
	public enum Type{
		FoodHarvested,
		FoodRegrown,
		FoodSpawned,
		BlobDied,
		BlobSpawned,
		BlobUpdated,
		NextFrame,
		LogAddBlob
	}
	
	public static class AgentEvent{
		public Agent agent;
		
		protected AgentEvent(Agent agent){
			this.agent = agent;
		}
	}
	
	public Type type;
	public AgentEvent agent;
	
	// creation methods
	
	static private SimulationEvent emptyEvent(Type type){
		SimulationEvent event = new SimulationEvent();
		event.type = type;
		return event;
	}
	
	static private SimulationEvent agentEvent(Type type, Agent agent){
		SimulationEvent event = new SimulationEvent();
		event.type = type;
		event.agent = new AgentEvent(agent);
		return event;
	}
	
	static public SimulationEvent foodHarvested(Agent food){
		return agentEvent(Type.FoodHarvested, food);
	}
	
	static public SimulationEvent foodRegrown(Agent food){
		return agentEvent(Type.FoodRegrown, food);
	}
	
	static public SimulationEvent foodSpawned(Agent food){
		return agentEvent(Type.FoodSpawned, food);
	}
	
	static public SimulationEvent blobDied(Agent blob){
		return agentEvent(Type.BlobDied, blob);
	}
	
	static public SimulationEvent blobUpdated(Agent blob){
		return agentEvent(Type.BlobUpdated, blob);
	}
	
	static public SimulationEvent blobSpawned(Agent blob){
		return agentEvent(Type.BlobSpawned, blob);
	}
	
	static public SimulationEvent logAddBlob(Agent blob){
		return agentEvent(Type.LogAddBlob, blob);
	}
	
	static public SimulationEvent nextFrame(){
		return emptyEvent(Type.NextFrame);
	}
}
