package zad1.simulation.log;

import zad1.simulation.Simulation;
import zad1.simulation.SimulationEvent;
import zad1.ecs.Agent;
import zad1.ecs.Engine;
import zad1.ecs.EngineSystem;

public class LogUpdateSystem extends EngineSystem{
	public LogUpdateSystem(Engine engine){
		super(engine);
	}
	
	@Override
	public void execute(Agent agent){
		Simulation.getSimulationEventEmitter().send(SimulationEvent.logAgent(agent));
	}
}
