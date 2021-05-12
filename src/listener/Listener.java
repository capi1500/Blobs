package listener;

public interface Listener<T>{
	void onSignal(T signal);
}
