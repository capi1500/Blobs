package simulation.agents.blob.commands;

import simulation.agents.blob.components.BlobComponent;
import utils.Direction;

public class TurnRight implements Command{
	@Override
	public void execute(BlobComponent blob){
		blob.setDirection(Direction.rotateRight(blob.getDirection()));
	}
	
	@Override
	public String toString(){
		return "TurnRight";
	}
}
