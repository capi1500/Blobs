package zad1.simulation.agents.components;

import zad1.ecs.Action;
import zad1.ecs.Component;
import zad1.simulation.agents.actions.Move;
import zad1.simulation.agents.actions.ScanAndGo;
import zad1.utils.vector.Vector2i;

public class PositionComponent extends SimpleComponent<Vector2i>{
	public PositionComponent(){
	}
	
	public PositionComponent(Vector2i obj){
		super(obj);
	}
	
	private PositionComponent(PositionComponent copy){
		set(copy.get().copy());
	}
	
	@Override
	public String getLog(){
		return "Position=" + get().toString();
	}
	
	@Override
	public void onSignal(Action signal){
		if(signal.getClass() == Move.class){
			set(((Move)signal).getTo());
		}
		else if(signal.getClass() == ScanAndGo.class){
			set(((ScanAndGo)signal).getTo());
		}
	}
	
	@Override
	public Component copy(){
		return new PositionComponent(this);
	}
}
