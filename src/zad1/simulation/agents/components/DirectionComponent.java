package zad1.simulation.agents.components;

import zad1.ecs.Action;
import zad1.ecs.Component;
import zad1.simulation.agents.actions.Scan;
import zad1.simulation.agents.actions.TurnLeft;
import zad1.simulation.agents.actions.TurnRight;
import zad1.utils.Direction;

public class DirectionComponent extends SimpleComponent<Direction>{
	public DirectionComponent(){
	}
	
	public DirectionComponent(Direction obj){
		super(obj);
	}
	
	public DirectionComponent(DirectionComponent copy){
		set(copy.get());
	}
	
	@Override
	public void onSignal(Action signal){
		if(signal.getClass() == Scan.class){
			Scan scan = (Scan)signal;
			if(scan.isDirectionSet())
				set(scan.getDirection());
		}
		else if(signal.getClass() == TurnLeft.class){
			set(Direction.rotateLeft(get()));
		}
		else if(signal.getClass() == TurnRight.class){
			set(Direction.rotateRight(get()));
		}
	}
	
	@Override
	public String getLog(){
		return "Direction=" + get().toString();
	}
	
	@Override
	public Component copy(){
		return new DirectionComponent(this);
	}
}
