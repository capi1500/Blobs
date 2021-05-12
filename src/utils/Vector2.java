package utils;

import simulation.interfaces.Copyable;

public abstract class Vector2<T> implements Copyable{
	public T x;
	public T y;
	
	// constructors
	
	public Vector2(T x, T y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2(){
		x = null;
		y = null;
	}
	
	// overrides
	
	@Override
	public String toString(){
		return "[" + x + ", " + y + "]";
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		
		Vector2<T> vector2 = (Vector2<T>)o;
		
		return x == vector2.x && y == vector2.y;
	}
}