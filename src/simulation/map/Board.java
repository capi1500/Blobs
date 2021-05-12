package simulation.map;

import utils.vector.Vector2i;

public class Board{
	private final Vector2i size;
	private final Field[][] fields;
	
	// constructors
	
	public Board(Vector2i size){
		this.size = size;
		fields = new Field[size.x][size.y];
		for(int x = 0; x < size.x; x++){
			for(int y = 0; y < size.y; y++){
				fields[x][y] = new Field();
			}
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
