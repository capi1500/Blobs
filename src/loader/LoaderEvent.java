package loader;

import utils.vector.Vector2i;

public class LoaderEvent{
	public enum EventType{
		TXT,
		JSON,
		BoardSize
	}
	
	public static class TxtEvent{
		public String command;
		public String value;
		
		public TxtEvent(String command, String value){
			this.command = command;
			this.value = value;
		}
	}
	
	public static class JsonEvent{
		public JsonEvent(){
		
		}
	}
	
	public EventType type;
	
	public TxtEvent txt;
	public JsonEvent json;
	public Vector2i boardSize;
	
	static public LoaderEvent txtEvent(String command, String value){
		LoaderEvent loaderEvent = new LoaderEvent();
		loaderEvent.type = EventType.TXT;
		loaderEvent.txt = new TxtEvent(command, value);
		return loaderEvent;
	}
	
	static public LoaderEvent jsonEvent(){
		LoaderEvent loaderEvent = new LoaderEvent();
		loaderEvent.type = EventType.JSON;
		loaderEvent.json = new JsonEvent();
		return loaderEvent;
	}
	
	static public LoaderEvent boardSizeEvent(int x, int y){
		LoaderEvent loaderEvent = new LoaderEvent();
		loaderEvent.type = EventType.BoardSize;
		loaderEvent.boardSize = new Vector2i(x, y);
		return loaderEvent;
	}
}
