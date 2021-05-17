package simulation.log;

import simulation.Simulation;
import simulation.SimulationEvent;
import simulation.agents.components.BlobComponent;
import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;

public class LogUpdateSystem extends EngineSystem{
	public LogUpdateSystem(Engine engine){
		super(engine);
	}
	
	@Override
	public void execute(Agent agent){
		Simulation.getSimulationEventEmitter().send(SimulationEvent.logAgent(agent));
	}
}
