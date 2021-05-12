package simulation.agents.blob;

import simulation.agents.blob.components.BlobComponent;
import ecs.Engine;
import simulation.agents.components.CircleGraphicComponent;
import simulation.agents.blob.components.ReplicableComponent;
import ecs.Agent;
import ecs.Factory;
import simulation.map.Board;
import utils.Direction;
import utils.vector.Vector2i;

public class BlobFactory extends Factory{
	private final BlobComponent blob;
	private final CircleGraphicComponent circle;
	private final ReplicableComponent replicable;
	
	// methods
	
	@Override
	public Agent spawn(Engine engine, Object... objects){
		if(objects.length != 2 || !Vector2i.class.isAssignableFrom(objects[1].getClass()) || !Board.class.isAssignableFrom(objects[0].getClass()))
			throw new UnsupportedOperationException("Wrong arguments for BlobFactory, should be [Board, Vector2i]");
		
		Board board = (Board)objects[0];
		Vector2i position = (Vector2i)objects[1];
		
		Agent agent = new Agent(engine);
		
		agent.addComponent(BlobComponent.class, blob.copy());
		agent.addComponent(CircleGraphicComponent.class, circle.copy());
		agent.addComponent(ReplicableComponent.class, replicable.copy());
		
		agent.getComponent(BlobComponent.class).setPosition(position);
		agent.getComponent(BlobComponent.class).setDirection(Direction.random());
		agent.getComponent(BlobComponent.class).setBoard(board);
		
		return agent;
	}
	
	// constructor
	
	public BlobFactory(BlobComponent blob, CircleGraphicComponent circle, ReplicableComponent replicable){
		this.blob = blob;
		this.circle = circle;
		this.replicable = replicable;
	}
}
