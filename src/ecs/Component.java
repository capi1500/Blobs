package ecs;

import interfaces.Copyable;
import interfaces.Creatable;
import interfaces.Destroyable;
import interfaces.Loggable;
import listener.Listener;

public abstract class Component implements Copyable, Creatable, Destroyable, Loggable, Listener<Action>{
	@Override
	public abstract Component copy();
	
	@Override
	public void onCreation(){}
	
	@Override
	public void onDestruction(){}
	
	@Override
	public String getLog(){
		return "\t\t" + toString() + "\n";
	}
}
