package zad1.ecs;

import zad1.interfaces.Destroyable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Engine implements Destroyable{
	private final HashMap<Class<? extends Component>, Integer> components;
	private final HashMap<Class<?>, Integer> tags;
	private final ArrayList<Agent> agents;
	private final ArrayList<EngineSystem> engineSystems;
	
	private boolean updating;
	private final ArrayList<Agent> agentsToAdd;
	private final ArrayList<Agent> agentsToRemove;
	
	// methods
	
	public void update(){
		updating = true;
		
		Collections.shuffle(agents);
		
		for(EngineSystem s : engineSystems){
			if(s.isActive()){
				for(Agent a : agents){
					if(s.matchesSignature(a))
						s.execute(a);
				}
			}
		}
		for(Agent agent : agents){
			agent.update();
		}
		updating = false;
		
		for(Agent agent : agents){
			if(!agent.isAlive())
				agentsToRemove.add(agent);
		}
		
		for(Agent agent : agentsToAdd){
			agent.onCreation();
			agents.add(agent);
		}
		agentsToAdd.clear();
		
		for(Agent agent : agentsToRemove){
			agent.onDestruction();
			agents.remove(agent);
		}
		agentsToRemove.clear();
	}
	
	public void addAgent(Agent agent){
		if(!updating){
			agent.onCreation();
			agents.add(agent);
		}
		else
			agentsToAdd.add(agent);
	}
	
	public void removeAgent(Agent agent){
		if(!updating){
			if(agents.remove(agent))
				agent.onDestruction();
		}
		else
			agentsToRemove.add(agent);
	}
	
	public void removeAllAgents(){
		for(Agent agent : agents)
			agent.onDestruction();
		for(Agent agent : agentsToAdd)
			agent.onDestruction();
		for(Agent agent : agentsToRemove)
			agent.onDestruction();
		agents.clear();
		agentsToAdd.clear();
		agentsToRemove.clear();
	}
	
	public void addSystem(EngineSystem engineSystem){
		if(updating)
			throw new UnsupportedOperationException("Cannot add system while updating");
		engineSystems.add(engineSystem);
	}
	
	public void removeSystem(EngineSystem engineSystem){
		if(updating)
			throw new UnsupportedOperationException("Cannot remove system while updating");
		engineSystems.remove(engineSystem);
	}
	
	public void removeAllSystems(){
		if(updating)
			throw new UnsupportedOperationException("Cannot remove systems while updating");
		engineSystems.clear();
	}
	
	// constructor
	
	public Engine(Class<?>... classes){
		components = new HashMap<>();
		tags = new HashMap<>();
		agents = new ArrayList<>();
		engineSystems = new ArrayList<>();
		
		for(Class<?> c : classes){
			if(Component.class.isAssignableFrom(c))
				components.put((Class<? extends Component>)c, components.size());
			else
				tags.put(c, tags.size());
		}
		
		updating = false;
		agentsToAdd = new ArrayList<>();
		agentsToRemove = new ArrayList<>();
	}
	
	// overrides
	
	@Override
	public void onDestruction(){
		removeAllSystems();
		removeAllAgents();
	}
	
	// components and tags getters
	
	public Set<Class<? extends Component>> getComponentList(){
		return components.keySet();
	}
	
	public Set<Class<?>> getTagList(){
		return tags.keySet();
	}
	
	public boolean isComponent(Class<? extends Component> component){
		return components.containsKey(component);
	}
	
	public boolean isTag(Class<?> tag){
		return tags.containsKey(tag);
	}
	
	public int componentId(Class<? extends Component> component){
		if(!isComponent(component))
			throw new UnsupportedOperationException("Class is not a component");
		return components.get(component);
	}
	
	public int tagId(Class<?> tag){
		if(!isTag(tag))
			throw new UnsupportedOperationException("Class is not a tag");
		return tags.get(tag);
	}
	
	public int componentBit(Class<? extends Component> component){
		return componentId(component);
	}
	
	public int tagBit(Class<?> tag){
		return componentCount() + tagId(tag);
	}
	
	public int componentCount(){
		return components.size();
	}
	
	public int tagCount(){
		return tags.size();
	}
	
	public int signatureSize(){
		return componentCount() + tagCount();
	}
}
