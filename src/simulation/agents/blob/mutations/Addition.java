package simulation.agents.blob.mutations;

import simulation.agents.blob.Mutation;
import simulation.agents.blob.components.BlobComponent;
import simulation.config.Config;

public class Addition implements Mutation{
	@Override
	public void mutate(BlobComponent blob){
		blob.getProgram().add(Config.getSimulationSettings().getRandomCommand());
	}
	
	@Override
	public String toString(){
		return "Addition";
	}
}
