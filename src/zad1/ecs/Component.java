package zad1.ecs;

import zad1.interfaces.Copyable;
import zad1.interfaces.Creatable;
import zad1.interfaces.Destroyable;
import zad1.interfaces.Loggable;
import zad1.listener.Listener;

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
