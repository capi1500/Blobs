package utils;

public class Path{
	private final String path;
	private final String extension;
	
	// constructors
	
	public Path(String path){
		this.path = path.toLowerCase();
		extension = path.substring(path.lastIndexOf('.'));
	}
	
	// getters and setters
	
	public String getPath(){
		return path;
	}
	
	public String getExtension(){
		return extension;
	}
}
