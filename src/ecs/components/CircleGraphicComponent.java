package ecs.components;

import simulation.config.Config;
import ecs.Component;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import simulation.interfaces.Loggable;

public class CircleGraphicComponent extends Component{
	private Circle shape;
	private Color color;
	
	// constructors
	
	public CircleGraphicComponent(Circle shape, Color color){
		this.shape = shape;
		this.color = color;
		shape.setFill(color);
	}
	
	private CircleGraphicComponent(CircleGraphicComponent copy){
		super(copy);
		shape = new Circle(copy.shape.getCenterX(), copy.shape.getCenterY(), copy.shape.getRadius());
		color = new Color(copy.color.getRed(), copy.color.getGreen(), copy.color.getBlue(), copy.color.getOpacity());
		shape.setFill(color);
	}
	
	// overrides
	
	@Override
	public CircleGraphicComponent copy(){
		return new CircleGraphicComponent(this);
	}
	
	@Override
	public void onCreation(){
		Config.getGraphicSettings().getFxGroup().getChildren().add(shape);
	}
	
	@Override
	public void onDestruction(){
		Config.getGraphicSettings().getFxGroup().getChildren().remove(shape);
	}
	
	@Override
	public String getLog(){
		return "CircleGraphicComponent{\n\t\t\tshape=" + shape + "\n\t\t}";
	}
	
	// getters and setters
	
	public Circle getCircle(){
		return shape;
	}
	
	public void setCircle(Circle shape){
		this.shape = shape;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
}
