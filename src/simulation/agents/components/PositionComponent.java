package simulation.agents.components;

import ecs.Action;
import ecs.Component;
import simulation.agents.actions.Move;
import simulation.agents.actions.ScanAndGo;
import utils.vector.Vector2i;

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
