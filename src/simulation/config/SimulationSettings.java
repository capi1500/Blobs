package simulation.config;

import simulation.agents.blob.BlobFactory;
import simulation.agents.blob.commands.Command;
import simulation.agents.food.FoodFactory;
import utils.Random;
import utils.vector.Vector2i;

public class SimulationSettings{
	private final Vector2i size;
	private final float foodChance;
	private final BlobFactory[] breeds;
	private final FoodFactory[] foods;
	private final Command[] commands;
	
	// constructor
	
	public SimulationSettings(Vector2i size, float foodChance, BlobFactory[] breeds, FoodFactory[] foods, Command[] commands){
		this.size = size;
		this.foodChance = foodChance;
		this.breeds = breeds;
		this.foods = foods;
		this.commands = commands;
	}
	
	// getters and setters
	
	public Vector2i getSize(){
		return size;
	}
	
	public FoodFactory[] getFoods(){
		return foods;
	}
	
	public BlobFactory[] getBreeds(){
		return breeds;
	}
	
	public float getFoodChance(){
		return foodChance;
	}
	
	public FoodFactory getRandomFood(){
		return foods[Random.getRandomNumberGenerator().nextInt(foods.length)];
	}
	
	public BlobFactory getRandomBreed(){
		return breeds[Random.getRandomNumberGenerator().nextInt(breeds.length)];
	}
	
	public Command getRandomCommand(){
		return commands[Random.getRandomNumberGenerator().nextInt(commands.length)];
	}
	
	public Command[] getCommands(){
		return commands;
	}
}
