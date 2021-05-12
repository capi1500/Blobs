package simulation.agents.blob.mutations;

import simulation.agents.blob.components.BlobComponent;
import simulation.agents.blob.Mutation;
import simulation.config.Config;

public class Replacement implements Mutation{
	@Override
	public void mutate(BlobComponent blob){
		if(!blob.getProgram().isEmpty()){
			blob.getProgram().remove(blob.getProgram().size() - 1);
			blob.getProgram().add(Config.getSimulationSettings().getRandomCommand());
		}
	}
	
	@Override
	public String toString(){
		return "Replacement";
	}
}
