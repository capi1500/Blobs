package simulation.agents.systems;

import simulation.agents.actions.Replicate;
import simulation.agents.blob.Mutation;
import simulation.agents.components.BlobComponent;
import simulation.agents.components.DirectionComponent;
import simulation.agents.components.ReplicableComponent;
import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;
import utils.Direction;
import utils.Pair;
import utils.Random;

public class ReplicateBlob extends EngineSystem{
	public ReplicateBlob(Engine engine){
		super(engine, BlobComponent.class, ReplicableComponent.class, DirectionComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		BlobComponent blob = agent.getComponent(BlobComponent.class);
		ReplicableComponent replicable = agent.getComponent(ReplicableComponent.class);
		DirectionComponent direction = agent.getComponent(DirectionComponent.class);
		
		if(agent.isAlive() && agent.isActive()){
			if(replicable.canReplicate() && Random.getRandomNumberGenerator().nextFloat() <= replicable.getReplicationChance()){
				agent.getActionEmitter().send(new Replicate());
				agent.getActionEmitter().processSignals();
				
				Agent newAgent = agent.copy();
				BlobComponent newBlob = newAgent.getComponent(BlobComponent.class);
				ReplicableComponent newReplicable = newAgent.getComponent(ReplicableComponent.class);
				DirectionComponent newDirection = newAgent.getComponent(DirectionComponent.class);
				
				newBlob.setAge(0);
				newBlob.setEnergy(blob.getEnergy() * replicable.getReplicationEnergyFraction());
				blob.useEnergy(newBlob.getEnergy());
				
				replicable.setReplicationCount(replicable.getReplicationCount() + 1);
				newReplicable.setReplicationCount(0);
				
				newDirection.set(Direction.switchSide(direction.get()));
				
				for(Pair<Mutation, Float> mutation : replicable.getMutations()){
					if(Random.getRandomNumberGenerator().nextFloat() <= mutation.second){
						mutation.first.mutate(newBlob);
					}
				}
				
				getEngine().addAgent(newAgent);
				
				if(blob.getEnergy() < 0)
					agent.kill();
			}
		}
	}
}
