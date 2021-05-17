package zad1.ecs;

import zad1.utils.Bitset;

public abstract class EngineSystem{
	private final Engine engine;
	
	private final Bitset signature;
	private boolean active;
	
	// methods
	
	public boolean matchesSignature(Agent agent){
		return agent.getSignature().and(signature).equals(signature);
	}
	
	public abstract void execute(Agent agent);
	
	// constructor
	
	public EngineSystem(Engine engine, Class<?>... signature){
		this.engine = engine;
		this.signature = new Bitset(engine.signatureSize());
		for(Class<?> c : signature){
			if(Component.class.isAssignableFrom(c)){
				Class<? extends Component> tmp = (Class<? extends Component>)c;
				if(engine.isComponent(tmp)){
					this.signature.set(engine.componentBit(tmp), true);
				}
			}
			if(engine.isTag(c))
				this.signature.set(engine.tagBit(c), true);
		}
		active = true;
	}
	
	// gerrers and setters
	
	public Engine getEngine(){
		return engine;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void activate(){
		active = true;
	}
	
	public void deactivate(){
		active = false;
	}
}
