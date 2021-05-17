package simulation;

import ecs.Agent;

public class SimulationEvent{
	public enum Type{
		AgentAdded,
		AgentRemoved,
		FoodHarvested,
		FoodRegrown,
		NextFrame,
		LogAgent
	}
	
	public Type type;
	public Agent agent;
	
	// creation methods
	
	static private SimulationEvent emptyEvent(Type type){
		SimulationEvent event = new SimulationEvent();
		event.type = type;
		return event;
	}
	
	static private SimulationEvent agentEvent(Type type, Agent agent){
		SimulationEvent event = new SimulationEvent();
		event.type = type;
		event.agent = agent;
		return event;
	}
	
	static public SimulationEvent agentAdded(Agent agent){
		return agentEvent(Type.AgentAdded, agent);
	}
	
	static public SimulationEvent agentRemoved(Agent agent){
		return agentEvent(Type.AgentRemoved, agent);
	}
	
	static public SimulationEvent foodHarvested(){
		return emptyEvent(Type.FoodHarvested);
	}
	
	static public SimulationEvent foodRegrown(){
		return emptyEvent(Type.FoodRegrown);
	}
	
	static public SimulationEvent logAgent(Agent agent){
		return agentEvent(Type.LogAgent, agent);
	}
	
	static public SimulationEvent nextFrame(){
		return emptyEvent(Type.NextFrame);
	}
}
