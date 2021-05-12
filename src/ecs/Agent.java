package ecs;

import simulation.Simulation;
import simulation.SimulationEvent;
import simulation.interfaces.Copyable;
import simulation.interfaces.Creatable;
import simulation.interfaces.Destroyable;
import simulation.interfaces.Loggable;
import utils.Bitset;

public class Agent implements Copyable, Creatable, Destroyable, Loggable{
	private final ComponentStorage componentStorage;
	private final Engine engine;
	
	private boolean active;
	private boolean alive;
	
	// constructors
	
	public Agent(Engine engine){
		this.engine = engine;
		componentStorage = new ComponentStorage(engine);
		active = true;
		alive = true;
	}
	
	private Agent(Agent copy){
		this.engine = copy.engine;
		componentStorage = copy.componentStorage.copy();
		active = copy.active;
		alive = copy.alive;
	}
	
	// overides
	
	@Override
	public void onCreation(){
		componentStorage.onCreation();
		Simulation.getSimulationEventEmitter().send(SimulationEvent.agentAdded(this));
	}
	
	@Override
	public void onDestruction(){
		componentStorage.onDestruction();
		Simulation.getSimulationEventEmitter().send(SimulationEvent.agentRemoved(this));
	}
	
	@Override
	public Agent copy(){
		return new Agent(this);
	}
	
	@Override
	public String getLog(){
		return "Agent{\n\tcomponentStorage=" + componentStorage.getLog() + "\n\tactive=" + active + "\n}";
	}
	
	// component and tag getters and setters
	
	public <T extends Component> T getComponent(Class<T> component){
		return (T)(componentStorage.getComponents()[engine.componentId(component)]);
	}
	
	public boolean hasComponent(Class<? extends Component> component){
		return componentStorage.getSignature().at(engine.componentBit(component));
	}
	
	public boolean hasTag(Class<?> tag){
		return componentStorage.getSignature().at(engine.tagBit(tag));
	}
	
	public <T extends Component> void addComponent(Class<T> component, T componentObject){
		componentStorage.getComponents()[engine.componentId(component)] = componentObject;
		componentStorage.getSignature().set(engine.componentBit(component), true);
	}
	
	public <T extends Component> void removeComponent(Class<T> component){
		componentStorage.getComponents()[engine.componentId(component)] = null;
		componentStorage.getSignature().set(engine.componentBit(component), false);
	}
	
	public <T> void addTag(Class<T> tag){
		componentStorage.getSignature().set(engine.tagBit(tag), true);
	}
	
	public <T> void removeTag(Class<T> tag){
		componentStorage.getSignature().set(engine.tagBit(tag), false);
	}
	
	public Bitset getSignature(){
		return componentStorage.getSignature();
	}
	
	// other getters and setters
	
	public void activate(){
		active = true;
	}
	
	public void deactivate(){
		active = false;
	}
	
	public void resurect(){
		alive = true;
	}
	
	public void kill(){
		alive = false;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public boolean isAlive(){
		return alive;
	}
}
