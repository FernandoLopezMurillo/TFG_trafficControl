package trabajo;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;
import trabajo.Car;
import trabajo.NetworkComponent;
import trabajo.NetworkTile;
import trabajo.TrafficLight;
import trabajo.NetworkComponent.ComponentType;


public class Style extends DefaultStyleOGL2D{

	@Override
	public Color getColor(Object obj)
	{
		NetworkComponent agent = (NetworkComponent) obj;
		switch (agent.getComponentType()) {
			case TRAFFIC_LIGHT:
				TrafficLight light = (TrafficLight) agent;
				return getTrafficLightColor(light);
			case CAR: 
				Car auto = (Car) agent;
				return getCarColor(auto);
			case TILE: 
				NetworkTile tile = (NetworkTile) agent;
				return getTileColor(tile);
			case CROSSROAD:
				return Color.gray;
			case MEASURE_POINT:
				return Color.cyan;
			default: return Color.pink;
		}
	}

	private Color getCarColor(Car agent) {
		switch (agent.getState()) {
			case DRIVING: 
				return Color.yellow;
			case WAITING:
				return Color.red;
			default:
				return Color.pink;

		}
	}

	private Color getTileColor(NetworkTile tile) {
		switch (tile.getTileType()) {
			case ROAD:
				return Color.gray;
			case ENDPOINT:
				return Color.green;
			case BLOCK:
				return Color.gray;
			default:
				return Color.pink;
		}
	}

	private Color getTrafficLightColor(TrafficLight light) {
		return light.isGreen() ? Color.green: Color.red;
	}
	
	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		
		ComponentType type = ((NetworkComponent) agent).getComponentType();
		
		if (spatial == null) {
			switch (type) {
			case TRAFFIC_LIGHT:
				spatial = shapeFactory.createCircle(5.0f, 8, true);
				break;
			case CAR:
				spatial = shapeFactory.createRectangle(12, 12, true);
			default:
				spatial = shapeFactory.createRectangle(15, 15);
			}	      
	    }
	    return spatial;
	}
}
