package ecs;

import ecs.actions.Activate;
import ecs.actions.Deactivate;
import ecs.actions.Kill;
import ecs.actions.Resurect;
import listener.Emitter;
import simulation.Simulation;
import simulation.SimulationEvent;
import interfaces.Copyable;
import interfaces.Creatable;
import interfaces.Destroyable;
import interfaces.Loggable;
import utils.Bitset;

public class Agent implements Copyable, Creatable, Destroyable, Loggable{
	private final ComponentStorage componentStorage;
	private final Engine engine;
	private final Emitter<Action> actionEmitter;
	
	private boolean active; // should be updated
	private boolean alive; // most agents can be dead
	
	// methods
	
	void update(){
		actionEmitter.processSignals();
	}
	
	// constructors
	
	public Agent(Engine engine){
		this.engine = engine;
		componentStorage = new ComponentStorage(engine);
		actionEmitter = new Emitter<>();
		
		active = true;
		alive = true;
	}
	
	private Agent(Agent copy){
		this.engine = copy.engine;
		componentStorage = copy.componentStorage.copy();
		actionEmitter = new Emitter<>();
		
		for(Component component : componentStorage.getComponents()){
			if(component != null)
				actionEmitter.addListener(component);
		}
		
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
		actionEmitter.removeAll();
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
		actionEmitter.addListener(componentObject);
	}
	
	public <T extends Component> void removeComponent(Class<T> component){
		actionEmitter.removeListener(componentStorage.getComponents()[engine.componentId(component)]);
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
		actionEmitter.sendNow(new Activate());
	}
	
	public void deactivate(){
		active = false;
		actionEmitter.sendNow(new Deactivate());
	}
	
	public void resurect(){
		alive = true;
		actionEmitter.sendNow(new Resurect());
	}
	
	public void kill(){
		alive = false;
		actionEmitter.sendNow(new Kill());
	}
	
	public boolean isActive(){
		return active;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public Emitter<Action> getActionEmitter(){
		return actionEmitter;
	}
}
