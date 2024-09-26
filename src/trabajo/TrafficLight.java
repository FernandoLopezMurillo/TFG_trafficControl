package trabajo;

import trabajo.GlobalConstants.Direction;
import trabajo.GlobalConstants.LightState;

public abstract class TrafficLight extends NetworkComponent {
	//Current state of the traffic light
	protected LightState currState;
	//Time the traffic light is going to be open
	protected int greenTime;
	//Time the traffic light is going to be close
	protected int redTime;
	protected int count;
	//The direction regulated by the traffic light 
	protected Direction direction;
	
	public boolean isGreen() {
		return currState.equals(LightState.GREEN);
	}
	
	public Direction getDirection() {
		return this.direction;
	}
}
