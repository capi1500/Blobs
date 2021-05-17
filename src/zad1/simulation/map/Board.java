package zad1.simulation.map;

import zad1.ecs.Agent;
import zad1.ecs.Engine;
import zad1.listener.Listener;
import zad1.loader.LoaderEvent;
import zad1.simulation.Simulation;
import zad1.simulation.SimulationEvent;
import zad1.simulation.config.Config;
import zad1.utils.vector.Vector2i;

public class Board implements Listener<LoaderEvent>{
	private final Engine engine;
	private Vector2i size;
	private Field[][] fields;
	private int fieldsLoaded = 0;
	
	// constructor
	
	public Board(Engine engine){
		this.engine = engine;
	}
	
	// overrides
	
	@Override
	public void onSignal(LoaderEvent signal){
		switch(signal.type){
			case TXT:
				if(signal.txt.command.equals("board_row")){
					for(int i = 0; i < signal.txt.value.length(); i++){
						if(signal.txt.value.charAt(i) == 'x'){
							Agent agent = Config.getSimulationSettings().getRandomFood().spawn(engine, new Vector2i(i, fieldsLoaded));
							fields[i][fieldsLoaded] = new Field(agent);
							engine.addAgent(agent);
							Simulation.getSimulationEventEmitter().send(SimulationEvent.logAgent(agent));
						}
						else if(signal.txt.value.charAt(i) != ' ')
							throw new UnsupportedOperationException("illegal board field, should be one of: {'x', ' '}");
					}
				}
				fieldsLoaded++;
				break;
			case JSON:
				break;
			case BoardSize:
				size = signal.boardSize;
				fields = new Field[size.x][size.y];
				for(int x = 0; x < size.x; x++){
					for(int y = 0; y < size.y; y++){
						fields[x][y] = new Field();
					}
				}
				break;
		}
	}
	
	
	// getters and setters
	
	public void setField(Vector2i position, Field field){
		fields[position.x][position.y] = field;
	}
	
	public Field getField(Vector2i position){
		if(fields[position.x][position.y] == null)
			System.out.println(position);
		return fields[position.x][position.y];
	}
	
	public Vector2i getSize(){
		return size;
	}
}
