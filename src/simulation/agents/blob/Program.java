package simulation.agents.blob;

import simulation.agents.blob.commands.Command;
import simulation.interfaces.Loggable;

import java.util.ArrayList;

public class Program extends ArrayList<Command> implements Loggable{
	// constructors
	
	public Program(int initialCapacity){
		super(initialCapacity);
	}
	
	public Program(){
	}
	
	public Program(Program copy){
		for(Command command : copy){
			add(command);
		}
	}
	
	public Program(Command... commands){
		for(Command c : commands){
			add(c);
		}
	}
	
	// override
	
	@Override
	public String getLog(){
		String content = "";
		for(int i = 0; i < size(); i++){
			content += get(i).toString();
			if(i + 1 != size())
				content += ", ";
		}
		return "[" + content + "]";
	}
}
