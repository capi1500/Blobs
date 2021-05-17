package simulation.config;

import javafx.scene.Group;
import listener.Listener;
import simulation.SimulationEvent;
import simulation.agents.components.BlobComponent;
import utils.vector.Vector2i;

public class GraphicSettings implements Listener<SimulationEvent>{
	private final Vector2i windowSize = new Vector2i(1600, 900);
	private int fieldSize;
	private int blobRadius;
	private final int frameTime = 2000; // in millis
	private int maxProgramLength = 0;
	
	private final Group fxGroup = new Group();
	
	public int getFieldSize(){
		return fieldSize;
	}
	
	public int getBlobRadius(){
		return blobRadius;
	}
	
	public int getFrameTime(){
		return frameTime;
	}
	
	public Vector2i getWindowSize(){
		return windowSize;
	}
	
	public void update(Vector2i size){
		fieldSize = Math.min(windowSize.x / size.x, windowSize.y / size.y);
		blobRadius = fieldSize / 3;
	}
	
	public Group getFxGroup(){
		return fxGroup;
	}
	
	public double getTickTime(){
		return (double)frameTime / (maxProgramLength + 1);
	}
	
	public void resetTickTime(){
		maxProgramLength = 0;
	}
	
	@Override
	public void onSignal(SimulationEvent signal){
		if(signal.type == SimulationEvent.Type.LogAgent || signal.type == SimulationEvent.Type.AgentAdded){
			if(signal.agent.hasComponent(BlobComponent.class))
				maxProgramLength = Math.max(maxProgramLength, signal.agent.getComponent(BlobComponent.class).getProgram().size());
		}
	}
}
