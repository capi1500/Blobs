package zad1.simulation.agents.components;

import zad1.ecs.Action;
import zad1.ecs.Agent;
import zad1.simulation.agents.actions.FoodHarvested;
import zad1.simulation.agents.actions.Move;
import zad1.simulation.agents.actions.ScanAndGo;
import zad1.simulation.agents.blob.Program;
import zad1.ecs.Component;
import zad1.simulation.map.Board;
import zad1.simulation.map.Field;
import zad1.utils.vector.Vector2i;

public class BlobComponent extends Component{
	private Board board;
	private final Program program;
	
	private int age;
	private float energy;
	private float energyUsage;
	
	// methods
	
	public void useEnergy(float count){
		setEnergy(energy - count);
	}
	
	private void onMove(Vector2i where, int time){
		Field field = board.getField(where);
		if(field.hasFoodToHarvest()){
			Agent food = field.getFood();
			energy += food.getComponent(FoodComponent.class).getEnergy();
			food.getActionEmitter().sendNow(new FoodHarvested(time));
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
		age = copy.age;
		energy = copy.energy;
		energyUsage = copy.energyUsage;
	}
	
	// overrides
	
	@Override
	public void onSignal(Action signal){
		if(signal.getClass() == Move.class)
			onMove(((Move)signal).getTo(), ((Move)signal).getTime());
		else if(signal.getClass() == ScanAndGo.class){
			ScanAndGo scanAndGo = (ScanAndGo)signal;
			if(!scanAndGo.getFrom().equals(scanAndGo.getTo()))
				onMove(scanAndGo.getTo(), scanAndGo.getTime());
		}
	}
	
	@Override
	public BlobComponent copy(){
		return new BlobComponent(this);
	}
	
	@Override
	public String getLog(){
		return "BlobComponent{\n\t\t\tprogram=" + program.getLog() +
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
	
	public void setEnergyUsage(float energyUsage){
		this.energyUsage = energyUsage;
	}
}
