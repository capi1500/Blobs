package zad1.simulation.agents.components;

import zad1.ecs.Action;
import zad1.ecs.Component;
import javafx.scene.paint.Color;
import zad1.simulation.agents.actions.FoodHarvested;
import zad1.simulation.agents.actions.FoodRegrown;

public class ColorComponent extends SimpleComponent<Color>{
	public ColorComponent(){
	}
	
	public ColorComponent(Color obj){
		super(obj);
	}
	
	private ColorComponent(ColorComponent copy){
		set(copy.get());
	}
	
	@Override
	public Component copy(){
		return new ColorComponent(this);
	}
	
	@Override
	public void onSignal(Action signal){
		if(signal.getClass() == FoodHarvested.class)
			set(Color.YELLOW);
		else if(signal.getClass() == FoodRegrown.class)
			set(Color.GREEN);
	}
}
