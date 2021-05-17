package simulation.agents.components;

import ecs.Component;

public abstract class SimpleComponent<T> extends Component{
	private T obj;
	
	// constructors
	
	public SimpleComponent(){
	}
	
	public SimpleComponent(T obj){
		this.obj = obj;
	}
	
	// getters and setters
	
	public T get(){
		return obj;
	}
	
	public void set(T obj){
		this.obj = obj;
	}
}
