package zad1.simulation.agents.blob.mutations;

import zad1.simulation.agents.components.BlobComponent;
import zad1.simulation.agents.blob.Mutation;
import zad1.simulation.config.Config;

public class Replacement implements Mutation{
	@Override
	public void mutate(BlobComponent blob){
		if(!blob.getProgram().isEmpty()){
			blob.getProgram().remove(blob.getProgram().size() - 1);
			blob.getProgram().add(Config.getSimulationSettings().getRandomBlobAction());
		}
	}
	
	@Override
	public String toString(){
		return "Replacement";
	}
}
