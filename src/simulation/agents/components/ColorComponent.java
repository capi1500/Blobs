package simulation.agents.components;

import ecs.Action;
import ecs.Component;
import javafx.scene.paint.Color;
import simulation.agents.actions.FoodHarvested;
import simulation.agents.actions.FoodRegrown;

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
