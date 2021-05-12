package simulation.agents.blob.commands;

import simulation.agents.blob.components.BlobComponent;
import simulation.map.Board;
import utils.vector.Vector2i;

import java.util.ArrayList;
import java.util.Collections;

public class ScanAndGo implements Command{
	@Override
	public void execute(BlobComponent blob){
		ArrayList<Vector2i> d = new ArrayList<>();
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i != 0 && j != 0)
					d.add(new Vector2i(i, j));
			}
		}
		Collections.shuffle(d);
		
		Vector2i pos = blob.getPosition();
		Board board = blob.getBoard();
		for(Vector2i delta : d){
			Vector2i pos2 = pos.add(delta);
			pos2.x = (pos2.x + board.getSize().x) % board.getSize().x;
			pos2.y = (pos2.y + board.getSize().y) % board.getSize().y;
			if(board.getField(pos2).hasFoodToHarvest()){
				blob.move(delta);
				break;
			}
		}
	}
	
	@Override
	public String toString(){
		return "ScanAndGo";
	}
}
