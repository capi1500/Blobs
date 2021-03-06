package zad1.simulation.agents.components;

import zad1.ecs.Action;
import zad1.simulation.agents.blob.Mutation;
import zad1.ecs.Component;
import zad1.utils.Pair;

import java.util.Arrays;

public class ReplicableComponent extends Component{
	private final float replicationEnergyFraction;
	private final Pair<Mutation, Float>[] mutations;
	private final int replicationLimit;
	private final float replicationChance;
	private int replicationCount;
	
	// constructors
	
	public ReplicableComponent(float replicationEnergyFraction, int replicationLimit, float replicationChance, Pair<Mutation, Float>... mutations){
		this.replicationEnergyFraction = replicationEnergyFraction;
		this.replicationChance = replicationChance;
		this.mutations = mutations;
		this.replicationLimit = replicationLimit;
		replicationCount = 0;
	}
	
	private ReplicableComponent(ReplicableComponent copy){
		replicationEnergyFraction = copy.replicationEnergyFraction;
		replicationChance = copy.replicationChance;
		mutations = copy.mutations;
		replicationLimit = copy.replicationLimit;
		replicationCount = copy.replicationCount;
	}
	
	// overrides
	
	@Override
	public void onSignal(Action signal){
		
	}
	
	@Override
	public ReplicableComponent copy(){
		return new ReplicableComponent(this);
	}
	
	@Override
	public String getLog(){
		return "ReplicableComponent{\n\t\t\treplicationEnergyFraction=" + replicationEnergyFraction +
								   "\n\t\t\tmutations=" + Arrays.toString(mutations) +
					   			   "\n\t\t\treplicationLimit=" + replicationLimit +
					   			   "\n\t\t\treplicationChance=" + replicationChance +
					   			   "\n\t\t\treplicationCount=" + replicationCount +
					   			   "\n\t\t}";
	}
	
	// getters and setters
	
	public float getReplicationEnergyFraction(){
		return replicationEnergyFraction;
	}
	
	public Pair<Mutation, Float>[] getMutations(){
		return mutations;
	}
	
	public void setReplicationCount(int replicationCount){
		this.replicationCount = replicationCount;
	}
	
	public int getReplicationCount(){
		return replicationCount;
	}
	
	public float getReplicationChance(){
		return replicationChance;
	}
	
	public boolean canReplicate(){
		return replicationCount < replicationLimit;
	}
}
