package simulation.config;

public class Config{
	private static final Config config = new Config();
	
	private GraphicSettings graphic;
	private SimulationSettings simulation;
	
	// constructor
	
	private Config(){
		graphic = new GraphicSettings();
		simulation = new SimulationSettings();
	}
	
	// getters and setters
	
	public static Config get(){
		return config;
	}
	
	public static GraphicSettings getGraphicSettings(){
		return config.graphic;
	}
	
	public static SimulationSettings getSimulationSettings(){
		return config.simulation;
	}
}
