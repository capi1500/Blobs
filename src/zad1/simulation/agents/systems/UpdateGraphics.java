package zad1.simulation.agents.systems;

import zad1.ecs.Agent;
import zad1.ecs.Engine;
import zad1.ecs.EngineSystem;
import zad1.simulation.agents.components.GraphicComponent;
import zad1.simulation.agents.components.PositionComponent;
import zad1.simulation.config.Config;

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
