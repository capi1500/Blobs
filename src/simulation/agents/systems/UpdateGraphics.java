package simulation.agents.systems;

import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;
import simulation.agents.components.BlobComponent;
import simulation.agents.components.GraphicComponent;
import simulation.agents.components.PositionComponent;
import simulation.config.Config;

public class UpdateGraphics extends EngineSystem{
	public UpdateGraphics(Engine engine){
		super(engine, GraphicComponent.class, PositionComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		GraphicComponent circle = agent.getComponent(GraphicComponent.class);
		PositionComponent position = agent.getComponent(PositionComponent.class);
		
		if(agent.isActive()){
			circle.get().setTranslateX(position.get().x * Config.getGraphicSettings().getFieldSize());
			circle.get().setTranslateY(position.get().y * Config.getGraphicSettings().getFieldSize());
			circle.update();
			circle.get().setVisible(true);
		}
	}
}
