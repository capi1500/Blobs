package simulation.agents.actions;

import ecs.Action;
import simulation.config.Config;
import utils.Direction;
import utils.vector.Vector2i;

public class Move extends TurnTimedAction{
	private final Vector2i from;
	private final Vector2i to;
	
	public Move(int time, Vector2i from, Direction direction){
		super(time);
		this.from = from;
		Vector2i delta = Vector2i.directionVector(direction), size = Config.getSimulationSettings().getSize();
		this.to = new Vector2i(
				(from.x + delta.x + size.x) % size.x,
				(from.y + delta.y + size.y) % size.y
		);
	}
	
	public Vector2i getFrom(){
		return from;
	}
	
	public Vector2i getTo(){
		return to;
	}
}
