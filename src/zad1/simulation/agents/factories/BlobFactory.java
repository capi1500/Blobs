package zad1.simulation.agents.factories;

import zad1.simulation.agents.components.*;
import zad1.ecs.Engine;
import zad1.ecs.Agent;
import zad1.ecs.Factory;
import zad1.simulation.config.Config;
import zad1.simulation.map.Board;
import zad1.utils.Direction;
import zad1.utils.vector.Vector2i;

public class BlobFactory extends Factory{
	private final BlobComponent blob;
	private final GraphicComponent circle;
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
		agent.addComponent(GraphicComponent.class, circle.copy());
		agent.addComponent(ReplicableComponent.class, replicable.copy());
		agent.addComponent(PositionComponent.class, new PositionComponent(position));
		agent.addComponent(DirectionComponent.class, new DirectionComponent(Direction.random()));
		
		agent.getComponent(GraphicComponent.class).get().setTranslateX(position.x * Config.getGraphicSettings().getFieldSize());
		agent.getComponent(GraphicComponent.class).get().setTranslateY(position.y * Config.getGraphicSettings().getFieldSize());
		
		agent.getComponent(BlobComponent.class).setBoard(board);
		
		return agent;
	}
	
	// constructor
	
	public BlobFactory(BlobComponent blob, GraphicComponent circle, ReplicableComponent replicable){
		this.blob = blob;
		this.circle = circle;
		this.replicable = replicable;
	}
}
