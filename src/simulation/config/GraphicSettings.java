package simulation.config;

import javafx.scene.Group;

public class GraphicSettings{
	private final int fieldSize = 15;
	private final int blobRadius = 5;
	private final int frameTime = 500;
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
	
	public Group getFxGroup(){
		return fxGroup;
	}
}
