package simulation.log;

import simulation.Simulation;
import simulation.SimulationEvent;
import simulation.agents.blob.components.BlobComponent;
import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;

public class LogUpdateSystem extends EngineSystem{
	public LogUpdateSystem(Engine engine){
		super(engine, BlobComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		Simulation.getSimulationEventEmitter().send(SimulationEvent.logAddBlob(agent));
	}
}
