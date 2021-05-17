package zad1.simulation.agents.actions;

import zad1.simulation.config.Config;
import zad1.simulation.map.Board;
import zad1.utils.vector.Vector2i;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScanAndGo extends TurnTimedAction{
	private final Vector2i from;
	private final Vector2i to;
	
	public ScanAndGo(int time, Vector2i from, Board board){
		super(time);
		this.from = from.copy();
		this.to = from.copy();
		
		List<Integer> xs = Arrays.asList(-1, 0, 1);
		List<Integer> ys = Arrays.asList(-1, 0, 1);
		
		Collections.shuffle(xs);
		Collections.shuffle(ys);
		
		boolean flg = false;
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				int dx = xs.get(i), dy = ys.get(j);
				
				if(dx == 0 && dy == 0)
					continue;
				
				Vector2i size = Config.getSimulationSettings().getSize();
				Vector2i target = new Vector2i(
						(from.x + dx + size.x) % size.x,
						(from.y + dy + size.y) % size.y
				);
				
				if(board.getField(target).hasFoodToHarvest()){
					this.to.x = target.x;
					this.to.y = target.y;
					flg = true;
					break;
				}
			}
			if(flg)
				break;
		}
	}
	
	public Vector2i getFrom(){
		return from;
	}
	
	public Vector2i getTo(){
		return to;
	}
}
