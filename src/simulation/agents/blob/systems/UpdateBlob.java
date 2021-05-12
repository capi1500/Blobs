package simulation.agents.blob.systems;

import simulation.config.Config;
import simulation.agents.blob.components.BlobComponent;
import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;
import simulation.agents.blob.commands.Command;
import ecs.components.CircleGraphicComponent;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class UpdateBlob extends EngineSystem{
	public UpdateBlob(Engine engine){
		super(engine, BlobComponent.class, CircleGraphicComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		BlobComponent blob = agent.getComponent(BlobComponent.class);
		CircleGraphicComponent circle = agent.getComponent(CircleGraphicComponent.class);
		
		SequentialTransition sequentialTransition = new SequentialTransition();
		sequentialTransition.setCycleCount(1);
		sequentialTransition.setAutoReverse(false);
		
		double frameTime = (double)(Config.getGraphicSettings().getFrameTime()) / blob.getProgram().size();
		
		if(agent.isAlive() && agent.isActive()){
			blob.setFrameTime(0);
			for(Command command : blob.getProgram()){
				blob.setFrameTime(blob.getFrameTime() + frameTime);
				
				TranslateTransition move = new TranslateTransition(Duration.millis(frameTime), circle.getCircle());
				move.setCycleCount(1);
				
				move.setFromX(blob.getPosition().x * Config.getGraphicSettings().getFieldSize());
				move.setFromY(blob.getPosition().y * Config.getGraphicSettings().getFieldSize());
				
				command.execute(blob);
				blob.useEnergy(1);
				
				move.setToX(blob.getPosition().x * Config.getGraphicSettings().getFieldSize());
				move.setToY(blob.getPosition().y * Config.getGraphicSettings().getFieldSize());
				
				double deltaX = Math.abs(move.getFromX() - move.getToX());
				double deltaY = Math.abs(move.getFromY() - move.getToY());
				if(deltaX > Config.getGraphicSettings().getFieldSize() || deltaY > Config.getGraphicSettings().getFieldSize()){
					move.setDuration(Duration.millis(frameTime / 2));
					
					TranslateTransition move2 = new TranslateTransition(Duration.millis(frameTime / 2), circle.getCircle());
					move2.setFromX(move.getFromX());
					move2.setFromY(move.getFromY());
					if(deltaX > Config.getGraphicSettings().getFieldSize()){
						if(blob.getPosition().x == 0)
							move2.setToX(Config.getSimulationSettings().getSize().x * Config.getGraphicSettings().getFieldSize());
						else
							move2.setToX(-Config.getGraphicSettings().getFieldSize());
					}
					if(deltaY > Config.getGraphicSettings().getFieldSize()){
						if(blob.getPosition().y == 0)
							move2.setToY(Config.getSimulationSettings().getSize().y * Config.getGraphicSettings().getFieldSize());
						else
							move2.setToY(-Config.getGraphicSettings().getFieldSize());
					}
					
					if(deltaX > Config.getGraphicSettings().getFieldSize()){
						if(blob.getPosition().x == 0)
							move.setFromX(-Config.getGraphicSettings().getFieldSize());
						else
							move.setFromX(Config.getSimulationSettings().getSize().x * Config.getGraphicSettings().getFieldSize());
					}
					if(deltaY > Config.getGraphicSettings().getFieldSize()){
						if(blob.getPosition().y == 0)
							move.setFromY(-Config.getGraphicSettings().getFieldSize());
						else
							move.setFromY(Config.getSimulationSettings().getSize().y * Config.getGraphicSettings().getFieldSize());
					}
					sequentialTransition.getChildren().add(move2);
				}
				
				sequentialTransition.getChildren().add(move);
				
				if(!agent.isAlive() || !agent.isActive()){
					FadeTransition fade = new FadeTransition(Duration.millis(frameTime), circle.getCircle());
					fade.setFromValue(1);
					fade.setToValue(0);
					fade.setCycleCount(1);
					fade.setAutoReverse(false);
					sequentialTransition.getChildren().add(fade);
					
					break;
				}
			}
			blob.useEnergy(blob.getEnergyUsage());
			blob.setAge(blob.getAge() + 1);
		}
		
		sequentialTransition.play();
	}
}
