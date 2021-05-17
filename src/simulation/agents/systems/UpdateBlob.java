package simulation.agents.systems;

import ecs.Action;
import simulation.agents.actions.*;
import simulation.agents.components.DirectionComponent;
import simulation.agents.components.PositionComponent;
import simulation.agents.components.BlobComponent;
import ecs.Agent;
import ecs.Engine;
import ecs.EngineSystem;

public class UpdateBlob extends EngineSystem{
	public UpdateBlob(Engine engine){
		super(engine, BlobComponent.class, PositionComponent.class, DirectionComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		BlobComponent blob = agent.getComponent(BlobComponent.class);
		PositionComponent position = agent.getComponent(PositionComponent.class);
		DirectionComponent direction = agent.getComponent(DirectionComponent.class);
		
		if(agent.isAlive() && agent.isActive()){
			int time = 0;
			for(Class<? extends Action> command : blob.getProgram()){
				
				if(Move.class.equals(command))
					agent.getActionEmitter().sendNow(new Move(time, position.get(), direction.get()));
				else if(Scan.class.equals(command))
					agent.getActionEmitter().sendNow(new Scan(time, position.get(), blob.getBoard()));
				else if(ScanAndGo.class.equals(command))
					agent.getActionEmitter().sendNow(new ScanAndGo(time, position.get(), blob.getBoard()));
				else if(TurnLeft.class.equals(command))
					agent.getActionEmitter().sendNow(new TurnLeft(time));
				else if(TurnRight.class.equals(command))
					agent.getActionEmitter().sendNow(new TurnRight(time));
				
				time++;
				blob.useEnergy(blob.getEnergyUsage());
				
				if(blob.getEnergy() < 0){
					agent.kill();
					return;
				}
			}
			blob.useEnergy(blob.getEnergyUsage());
			blob.setAge(blob.getAge() + 1);
			if(blob.getEnergy() < 0)
				agent.kill();
		}
	}
}
