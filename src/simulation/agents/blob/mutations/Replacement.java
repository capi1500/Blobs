package simulation.agents.blob.mutations;

import simulation.agents.components.BlobComponent;
import simulation.agents.blob.Mutation;
import simulation.config.Config;

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
