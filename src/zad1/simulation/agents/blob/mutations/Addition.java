package zad1.simulation.agents.blob.mutations;

import zad1.simulation.agents.blob.Mutation;
import zad1.simulation.agents.components.BlobComponent;
import zad1.simulation.config.Config;

public class Addition implements Mutation{
	@Override
	public void mutate(BlobComponent blob){
		blob.getProgram().add(Config.getSimulationSettings().getRandomBlobAction());
	}
	
	@Override
	public String toString(){
		return "Addition";
	}
}
