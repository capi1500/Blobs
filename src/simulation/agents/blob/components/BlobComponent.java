package simulation.agents.blob.components;

import simulation.Simulation;
import simulation.SimulationEvent;
import simulation.agents.blob.Program;
import ecs.Component;
import simulation.map.Board;
import simulation.map.Field;
import utils.Direction;
import utils.vector.Vector2i;

public class BlobComponent implements Component{
	private Board board;
	private final Program program;
	
	private Vector2i position;
	private Direction direction;
	private int age;
	private float energy;
	private final float energyUsage;
	
	private double frameTime;
	
	// methods
	
	public void useEnergy(float count){
		setEnergy(energy - count);
	}
	
	public void move(Vector2i delta){
		position.translate(delta);
		position.x = (position.x + board.getSize().x) % board.getSize().x;
		position.y = (position.y + board.getSize().y) % board.getSize().y;
		
		Field field = board.getField(position);
		if(field.hasFoodToHarvest()){
			energy += field.getFood().getEnergy();
			field.getFood().harvest(frameTime);
		}
	}
	
	// constructor
	
	public BlobComponent(Program program, float energy, float energyUsage){
		this.program = program;
		this.energy = energy;
		this.energyUsage = energyUsage;
		this.age = 0;
	}
	
	private BlobComponent(BlobComponent copy){
		board = copy.board;
		if(copy.program == null)
			program = new Program();
		else
			program = new Program(copy.program);
		if(copy.position == null)
			position = new Vector2i();
		else
			position = copy.position.copy();
		direction = copy.direction;
		age = copy.age;
		energy = copy.energy;
		energyUsage = copy.energyUsage;
		frameTime = copy.frameTime;
	}
	
	// overrides
	
	@Override
	public BlobComponent copy(){
		return new BlobComponent(this);
	}
	
	@Override
	public void onCreation(){
	
	}
	
	@Override
	public void onDestruction(){
	
	}
	
	@Override
	public String getLog(){
		return "BlobComponent{\n\t\t\tprogram=" + program +
					   		 "\n\t\t\tposition=" + position +
					   		 "\n\t\t\tdirection=" + direction +
					   		 "\n\t\t\tage=" + age +
					   		 "\n\t\t\tenergy=" + energy +
					   		 "\n\t\t\tenergyUsage=" + energyUsage +
					   		 "\n\t\t}";
	}
	// getters and setters
	
	public Board getBoard(){
		return board;
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	public Program getProgram(){
		return program;
	}
	
	public Vector2i getPosition(){
		return position;
	}
	
	public void setPosition(Vector2i position){
		this.position = position;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public int getAge(){
		return age;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public float getEnergy(){
		return energy;
	}
	
	public void setEnergy(float energy){
		this.energy = energy;
	}
	
	public float getEnergyUsage(){
		return energyUsage;
	}
	
	public double getFrameTime(){
		return frameTime;
	}
	
	public void setFrameTime(double frameTime){
		this.frameTime = frameTime;
	}
	
	public boolean isAlive(){
		return energy > 0;
	}
}
