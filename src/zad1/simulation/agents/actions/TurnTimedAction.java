package zad1.simulation.agents.actions;

import zad1.ecs.Action;

public class TurnTimedAction implements Action{
	protected int time;
	
	public TurnTimedAction(int time){
		this.time = time;
	}
	
	public int getTime(){
		return time;
	}
}
