package simulation.agents.blob.commands;

import simulation.agents.blob.components.BlobComponent;
import simulation.map.Board;
import utils.Direction;
import utils.vector.Vector2i;

import java.util.ArrayList;
import java.util.Collections;

public class Scan implements Command{
	@Override
	public void execute(BlobComponent blob){
		ArrayList<Direction> d = new ArrayList<>();
		for(int i = 0; i < 4; i++){
			d.add(Direction.fromValue(i));
		}
		Collections.shuffle(d);
		
		Vector2i pos = blob.getPosition();
		Board board = blob.getBoard();
		for(Direction dir : d){
			Vector2i pos2 = pos.add(Vector2i.directionVector(dir));
			pos2.x = (pos2.x + board.getSize().x) % board.getSize().x;
			pos2.y = (pos2.y + board.getSize().y) % board.getSize().y;
			if(board.getField(pos2).hasFoodToHarvest()){
				blob.setDirection(dir);
				break;
			}
		}
	}
	
	@Override
	public String toString(){
		return "Scan";
	}
}
