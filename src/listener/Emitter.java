package listener;

import simulation.interfaces.Destroyable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Emitter<T> implements Destroyable{
	private final ArrayList<Listener<T>> listeners;
	private final Queue<T> signals;
	
	// methods
	
	public void send(T signal){
		signals.add(signal);
	}
	
	public void processSignals(){
		while(!signals.isEmpty()){
			T signal = signals.poll();
			for(Listener<T> listener : listeners)
				listener.onSignal(signal);
		}
	}
	
	public void addListener(Listener<T> listener){
		listeners.add(listener);
	}
	
	public void removeListener(Listener<T> listener){
		listeners.remove(listener);
	}
	
	public void removeAll(){
		listeners.clear();
	}
	
	// constructor
	
	public Emitter(){
		listeners = new ArrayList<>();
		signals = new LinkedList<>();
	}
	
	// overrides
	
	@Override
	public void onDestruction(){
		removeAll();
	}
}
