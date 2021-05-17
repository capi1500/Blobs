package zad1.utils.vector;

import zad1.utils.Direction;
import zad1.utils.Vector2;

public class Vector2i extends Vector2<Integer>{
	
	// methods
	
	public Vector2i translate(Vector2i v){
		x = x + v.x;
		y = y + v.y;
		return this;
	}
	
	public Vector2i add(Vector2i v){
		return this.copy().translate(v);
	}
	
	public Vector2i scale(int s){
		x = x * s;
		y = y * s;
		return this;
	}
	
	public Vector2i multiply(int s){
		return this.copy().scale(s);
	}
	
	// creation methods
	
	static public Vector2i directionVector(Direction direction){
		switch(direction){
			case Up:
				return up();
			case Down:
				return down();
			case Left:
				return left();
			case Right:
				return right();
		}
		return new Vector2i();
	}
	
	static public Vector2i up(){
		return new Vector2i(0, 1);
	}
	
	static public Vector2i down(){
		return new Vector2i(0, -1);
	}
	
	static public Vector2i left(){
		return new Vector2i(-1, 0);
	}
	
	static public Vector2i right(){
		return new Vector2i(1, 0);
	}
	
	// constructors
	
	public Vector2i(Integer x, Integer y){
		super(x, y);
	}
	
	public Vector2i(){
	}
	
	private Vector2i(Vector2i copy){
		x = copy.x;
		y = copy.y;
	}
	
	// overrides
	
	@Override
	public Vector2i copy(){
		return new Vector2i(this);
	}
}
