package simulation.agents.blob.commands;

import simulation.agents.blob.components.BlobComponent;
import utils.Direction;

public class TurnLeft implements Command{
	@Override
	public void execute(BlobComponent blob){
		blob.setDirection(Direction.rotateLeft(blob.getDirection()));
	}
	
	@Override
	public String toString(){
		return "TurnLeft";
	}
}
