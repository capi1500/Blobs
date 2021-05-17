package zad1.simulation.agents.actions;

import zad1.simulation.config.Config;
import zad1.simulation.map.Board;
import zad1.utils.Direction;
import zad1.utils.vector.Vector2i;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Scan extends TurnTimedAction{
	private final Vector2i where;
	private Direction direction;
	private boolean directionSet;
	
	public Scan(int time, Vector2i where, Board board){
		super(time);
		this.where = where.copy();
		this.directionSet = false;
		
		List<Direction> ds = Arrays.asList(
				Direction.Up,
				Direction.Down,
				Direction.Left,
				Direction.Right
		);
		
		Collections.shuffle(ds);
		
		for(int i = 0; i < 4; i++){
			Direction dir = ds.get(i);
			Vector2i delta = Vector2i.directionVector(dir);
			
			Vector2i size = Config.getSimulationSettings().getSize();
			Vector2i target = new Vector2i(
					(where.x + delta.x + size.x) % size.x,
					(where.y + delta.y + size.y) % size.y
			);
			
			if(board.getField(target).hasFoodToHarvest()){
				direction = dir;
				directionSet = true;
				break;
			}
		}
	}
	
	public Vector2i getWhere(){
		return where;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public boolean isDirectionSet(){
		return directionSet;
	}
}
