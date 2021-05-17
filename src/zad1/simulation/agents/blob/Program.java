package zad1.simulation.agents.blob;

import zad1.ecs.Action;
import zad1.interfaces.Loggable;

import java.util.ArrayList;
import java.util.Arrays;

public class Program extends ArrayList<Class<? extends Action>> implements Loggable{
	// constructors

	public Program(int initialCapacity){
		super(initialCapacity);
	}
	
	public Program(){
	}
	
	public Program(Program copy){
		this.addAll(copy);
	}
	
	public Program(Class<? extends Action>... actions){
		this.addAll(Arrays.asList(actions));
	}
	
	// override
	
	@Override
	public String getLog(){
		StringBuilder content = new StringBuilder();
		for(int i = 0; i < size(); i++){
			String fullName = get(i).getTypeName();
			content.append(fullName.substring(fullName.lastIndexOf('.') + 1));
			if(i + 1 != size())
				content.append(", ");
		}
		return "[" + content + "]";
	}
}
