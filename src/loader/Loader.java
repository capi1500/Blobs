package loader;

import listener.Emitter;
import listener.Listener;
import simulation.config.Config;
import utils.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader{
	static private final Emitter<LoaderEvent> loader = new Emitter<>();
	
	static public void loadBoard(Path path){
		try{
			Scanner file = new Scanner(new File(path.getPath()));
			Scanner file2 = new Scanner(new File(path.getPath()));
			
			int x = -1, y = 0;
			
			while(file2.hasNextLine()){
				if(x == -1)
					x = file2.nextLine().length();
				else if(file2.nextLine().length() != x)
					throw new UnsupportedOperationException("board row size differs");
				y++;
			}
			
			loader.sendNow(LoaderEvent.boardSizeEvent(x, y));
			Config.getSimulationSettings().finalizeTxt();
			
			while(file.hasNextLine()){
				loader.send(LoaderEvent.txtEvent("board_row", file.nextLine()));
			}
			
			loader.processSignals();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	static public void load(Path path){
		try{
			Scanner file = new Scanner(new File(path.getPath()));
			
			if(path.getExtension().equals(".txt")){
				String command;
				String value;
				while(file.hasNext()){
					command = file.next();
					if(!file.hasNext())
						throw new UnsupportedOperationException(path.getPath() + " is illegally formatted");
					value = file.next();
					loader.send(LoaderEvent.txtEvent(command, value));
				}
			}
			else if(path.getExtension().equals(".json")){
			
			}
			else{
				throw new UnsupportedOperationException("extension " + path.getExtension() + " not supported");
			}
			
			loader.processSignals();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public static void addListener(Listener<LoaderEvent> listener){
		loader.addListener(listener);
	}
	
	public static void removeListener(Listener<LoaderEvent> listener){
		loader.removeListener(listener);
	}
	
	public static void removeAllListeners(){
		loader.removeAll();
	}
}
