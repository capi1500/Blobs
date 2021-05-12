package simulation.agents.blob.systems;

import simulation.agents.blob.Mutation;
import simulation.agents.blob.components.BlobComponent;
import simulation.agents.blob.components.ReplicableComponent;
import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;
import utils.Direction;
import utils.Pair;
import utils.Random;

public class ReplicateBlob extends EngineSystem{
	public ReplicateBlob(Engine engine){
		super(engine, BlobComponent.class, ReplicableComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		BlobComponent blob = agent.getComponent(BlobComponent.class);
		ReplicableComponent replicable = agent.getComponent(ReplicableComponent.class);
		
		if(agent.isAlive() && agent.isActive() && blob.isAlive()){
			if(replicable.canReplicate() && Random.getRandomNumberGenerator().nextFloat() <= replicable.getReplicationChance()){
				Agent newAgent = agent.copy();
				BlobComponent newBlob = newAgent.getComponent(BlobComponent.class);
				ReplicableComponent newReplicable = newAgent.getComponent(ReplicableComponent.class);
				
				newBlob.setAge(0);
				newBlob.setEnergy(blob.getEnergy() * replicable.getReplicationEnergyFraction());
				blob.useEnergy(newBlob.getEnergy());
				
				newReplicable.setReplicationCount(0);
				
				newBlob.setDirection(Direction.switchSide(newBlob.getDirection()));
				for(Pair<Mutation, Float> mutation : replicable.getMutations()){
					if(Random.getRandomNumberGenerator().nextFloat() <= mutation.second){
						mutation.first.mutate(newBlob);
					}
				}
				
				getEngine().addAgent(newAgent);
				
				if(!blob.isAlive())
					agent.kill();
			}
		}
	}
}
