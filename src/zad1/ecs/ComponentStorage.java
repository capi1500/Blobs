package zad1.ecs;

import zad1.interfaces.Copyable;
import zad1.interfaces.Creatable;
import zad1.interfaces.Destroyable;
import zad1.interfaces.Loggable;
import zad1.utils.Bitset;

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
		StringBuilder out = new StringBuilder("[\n");
		for(Component component : components){
			if(component != null)
				out.append("\t\t").append(component.getLog()).append("\n");
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
