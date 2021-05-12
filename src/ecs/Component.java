package ecs;

import simulation.interfaces.Copyable;
import simulation.interfaces.Creatable;
import simulation.interfaces.Destroyable;
import simulation.interfaces.Loggable;

public abstract class Component implements Copyable, Creatable, Destroyable, Loggable{
	private Agent agent;
	// constructors
	
	public Component(){}
	
	protected Component(Component copy){
		agent = copy.agent;
	}
	
	// overrides
	
	@Override
	public abstract Component copy();
	
	// getters and setters
	
	public Agent getAgent(){
		return agent;
	}
	
	public void setAgent(Agent agent){
		this.agent = agent;
	}
	
}
