package ecs;

import interfaces.Copyable;
import interfaces.Creatable;
import interfaces.Destroyable;
import interfaces.Loggable;
import utils.Bitset;

public class ComponentStorage implements Copyable, Creatable, Destroyable, Loggable{
	private final Component[] components;
	private final Bitset signature;
	private final Engine engine;
	
	// constructor
	
	public ComponentStorage(Engine engine){
		this.engine = engine;
		signature = new Bitset(engine.signatureSize());
		components = new Component[engine.componentCount()];
	}
	
	private ComponentStorage(ComponentStorage copy){
		this.engine = copy.engine;
		signature = copy.signature.copy();
		components = new Component[engine.componentCount()];
		for(int i = 0; i < engine.componentCount(); i++){
			if(copy.components[i] != null){
				components[i] = copy.components[i].copy();
			}
		}
	}
	
	// overrides
	
	@Override
	public ComponentStorage copy(){
		return new ComponentStorage(this);
	}
	
	@Override
	public void onCreation(){
		for(Component component : components){
			if(component != null)
				component.onCreation();
		}
	}
	
	@Override
	public void onDestruction(){
		for(Component component : components){
			if(component != null)
				component.onDestruction();
		}
	}
	
	@Override
	public String getLog(){
		String out = "[\n";
		for(Component component : components){
			if(component != null)
				out += "\t\t" + component.getLog() + "\n";
		}
		return out + "\t]";
	}
	
	// getters and setters
	
	public Component[] getComponents(){
		return components;
	}
	
	public Bitset getSignature(){
		return signature;
	}
	
	public Engine getEngine(){
		return engine;
	}
}
