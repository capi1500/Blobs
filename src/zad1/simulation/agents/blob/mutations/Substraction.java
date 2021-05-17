package zad1.simulation.agents.blob.mutations;

import zad1.simulation.agents.components.BlobComponent;
import zad1.simulation.agents.blob.Mutation;

public class Substraction implements Mutation{
	@Override
	public void mutate(BlobComponent blob){
		if(!blob.getProgram().isEmpty())
			blob.getProgram().remove(blob.getProgram().size() - 1);
	}
	
	@Override
	public String toString(){
		return "Substraction";
	}
}
