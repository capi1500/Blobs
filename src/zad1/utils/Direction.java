package zad1.utils;

public enum Direction{
	Up(0),
	Right(1),
	Down(2),
	Left(3);
	
	private final int value;
	
	// methods
	
	static public Direction rotateLeft(Direction direction){
		return fromValue((direction.value + 3) % 4);
	}
	
	static public Direction rotateRight(Direction direction){
		return fromValue((direction.value + 1) % 4);
	}
	
	static public Direction switchSide(Direction direction){
		return fromValue((direction.value + 2) % 4);
	}
	
	static public Direction fromValue(int value){
		if(value == 0)
			return Up;
		else if(value == 1)
			return Right;
		else if(value == 2)
			return Down;
		return Left;
	}
	
	static public Direction random(){
		return fromValue(Random.getRandomNumberGenerator().nextInt(4));
	}
	
	public int getValue(){
		return this.value;
	}
	
	// constructor
	
	Direction(int value){
		this.value = value;
	}
}
