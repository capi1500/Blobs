package zad1.simulation.config;

import zad1.ecs.Action;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import zad1.listener.Listener;
import zad1.loader.LoaderEvent;
import zad1.simulation.agents.actions.*;
import zad1.simulation.agents.factories.BlobFactory;
import zad1.simulation.agents.blob.Program;
import zad1.simulation.agents.components.BlobComponent;
import zad1.simulation.agents.components.ReplicableComponent;
import zad1.simulation.agents.blob.mutations.Addition;
import zad1.simulation.agents.blob.mutations.Replacement;
import zad1.simulation.agents.blob.mutations.Substraction;
import zad1.simulation.agents.components.GraphicComponent;
import zad1.simulation.agents.factories.FoodFactory;
import zad1.simulation.agents.components.FoodComponent;
import zad1.utils.Pair;
import zad1.utils.Random;
import zad1.utils.vector.Vector2i;

import java.util.ArrayList;

public class SimulationSettings implements Listener<LoaderEvent>{
	private Vector2i size;
	private BlobFactory[] breeds;
	private FoodFactory[] foods;
	private ArrayList<Class<? extends Action>> blobActions;
	
	private int simulationTime = -1;
	private int startingBlobCount = -1;
	private int logTime = -1;
	
	// txt loading
	private String parameters;
	private String board;
	// blob
	private Program program = null;
	private float blobEnergy = -1;
	private float energyUsage = -1;
	// replicable
	private float replicationEnergyFraction = -1;
	private int replicationLimit = -1;
	private float replicationChance = -1;
	private float additionChance = -1;
	private float subtractionChance = -1;
	private float replacementChance = -1;
	//food
	private int foodEnergy = -1;
	private int growthTime = -1;
	
	// methods
	
	public void initTxt(){
		blobActions = new ArrayList<>();
		blobActions.add(ScanAndGo.class);
		blobActions.add(Scan.class);
		blobActions.add(Move.class);
		blobActions.add(TurnLeft.class);
		blobActions.add(TurnRight.class);
	}
	
	public void finalizeTxt(){
		breeds = new BlobFactory[1];
		foods = new FoodFactory[1];
		breeds[0] = new BlobFactory(
				new BlobComponent(
						program,
						blobEnergy,
						energyUsage
				),
				new GraphicComponent(
						new Circle(
							Config.getGraphicSettings().getBlobRadius(),
							Color.BLUE
						)
				),
				new ReplicableComponent(
						replicationEnergyFraction,
						replicationLimit,
						replicationChance,
						new Pair<>(new Substraction(), subtractionChance),
						new Pair<>(new Addition(), additionChance),
						new Pair<>(new Replacement(), replacementChance)
				)
		);
		foods[0] = new FoodFactory(
				new FoodComponent(
						foodEnergy,
						growthTime
				),
				new GraphicComponent(new Circle(Config.getGraphicSettings().getBlobRadius() * 3.0 / 2, Color.GREEN))
		);
	}
	
	// overrides
	
	@Override
	public void onSignal(LoaderEvent signal){
		switch(signal.type){
			case TXT:
				switch(signal.txt.command){
					case "ile_tur":
						if(simulationTime != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						simulationTime = Integer.parseInt(signal.txt.value);
						if(simulationTime < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
					case "pocz_ile_robów":
						if(startingBlobCount != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						startingBlobCount = Integer.parseInt(signal.txt.value);
						if(startingBlobCount < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
					case "pocz_progr":
						if(program != null)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						program = new Program();
						for(int i = 0; i < signal.txt.value.length(); i++){
							char cmd = signal.txt.value.charAt(i);
							if(cmd == 'l')
								program.add(TurnLeft.class);
							else if(cmd == 'p')
								program.add(TurnRight.class);
							else if(cmd == 'w')
								program.add(Scan.class);
							else if(cmd == 'j')
								program.add(ScanAndGo.class);
							else if(cmd == 'i')
								program.add(Move.class);
							else
								throw new UnsupportedOperationException("invalid command, should be from: {'l', 'p', 'w', 'i', 'j'}");
						}
						break;
					case "pocz_energia":
						if(blobEnergy != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						blobEnergy = Integer.parseInt(signal.txt.value);
						if(blobEnergy < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
					case "ile_daje_jedzenie":
						if(foodEnergy != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						foodEnergy = Integer.parseInt(signal.txt.value);
						if(foodEnergy < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
					case "ile_rośnie_jedzenie":
						if(growthTime != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						growthTime = Integer.parseInt(signal.txt.value);
						if(growthTime < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
					case "koszt_tury":
						if(energyUsage != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						energyUsage = Integer.parseInt(signal.txt.value);
						if(energyUsage < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
					case "pr_powielania":
						if(replicationChance != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						replicationChance = Float.parseFloat(signal.txt.value);
						if(replicationChance < 0 || replicationChance > 1)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be between [0, 1]");
						break;
					case "ułamek_energii_rodzica":
						if(replicationEnergyFraction != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						replicationEnergyFraction = Float.parseFloat(signal.txt.value);
						if(replicationEnergyFraction < 0 || replicationEnergyFraction > 1)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be between [0, 1]");
						break;
					case "limit_powielania":
						if(replicationLimit != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						replicationLimit = Integer.parseInt(signal.txt.value);
						if(replicationLimit < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
					case "pr_usunięcia_instrukcji":
						if(subtractionChance != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						subtractionChance = Float.parseFloat(signal.txt.value);
						if(subtractionChance < 0 || subtractionChance > 1)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be between [0, 1]");
						break;
					case "pr_dodania_instrukcji":
						if(additionChance != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						additionChance = Float.parseFloat(signal.txt.value);
						if(additionChance < 0 || additionChance > 1)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be between [0, 1]");
						break;
					case "pr_zmiany_instrukcji":
						if(replacementChance != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						replacementChance = Float.parseFloat(signal.txt.value);
						if(replacementChance < 0 || replacementChance > 1)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be between [0, 1]");
						break;
					case "co_ile_wypisz":
						if(logTime != -1)
							throw new UnsupportedOperationException("multiple definitions of " + signal.txt.command);
						logTime = Integer.parseInt(signal.txt.value);
						if(logTime < 0)
							throw new NumberFormatException("invalid " + signal.txt.command + ", should be positive");
						break;
				}
				break;
			case JSON:
				break;
			case BoardSize:
				size = signal.boardSize;
				Config.getGraphicSettings().update(size);
				break;
		}
	}
	
	// getters and setters
	
	public Vector2i getSize(){
		return size;
	}
	
	public int getSimulationTime(){
		return simulationTime;
	}
	
	public int getStartingBlobCount(){
		return startingBlobCount;
	}
	
	public int getLogTime(){
		return logTime;
	}
	
	public FoodFactory[] getFoods(){
		return foods;
	}
	
	public BlobFactory[] getBreeds(){
		return breeds;
	}
	
	public FoodFactory getRandomFood(){
		return foods[Random.getRandomNumberGenerator().nextInt(foods.length)];
	}
	
	public BlobFactory getRandomBreed(){
		return breeds[Random.getRandomNumberGenerator().nextInt(breeds.length)];
	}
	
	public Class<? extends Action> getRandomBlobAction(){
		return blobActions.get(Random.getRandomNumberGenerator().nextInt(blobActions.size()));
	}
	
	public Class<? extends Action>[] getBlobActions(){
		return blobActions.toArray(new Class[0]);
	}
	
	public String getParameters(){
		return parameters;
	}
	
	public void setParameters(String parameters){
		this.parameters = parameters;
	}
	
	public String getBoard(){
		return board;
	}
	
	public void setBoard(String board){
		this.board = board;
	}
}
