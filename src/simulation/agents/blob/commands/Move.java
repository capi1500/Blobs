package simulation.agents.blob.commands;

import simulation.agents.blob.components.BlobComponent;
import utils.vector.Vector2i;

public class Move implements Command{
	@Override
	public void execute(BlobComponent blob){
		blob.move(Vector2i.directionVector(blob.getDirection()));
	}
	
	@Override
	public String toString(){
		return "Move";
	}
}
