package simulation.agents.actions;

import ecs.Action;

public class TurnTimedAction implements Action{
	protected int time;
	
	public TurnTimedAction(int time){
		this.time = time;
	}
	
	public int getTime(){
		return time;
	}
}
