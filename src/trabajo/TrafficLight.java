package trabajo;

import repast.simphony.engine.schedule.ScheduledMethod;
import trabajo.GlobalConstants.*;

public class TrafficLight extends NetworkComponent{
	
	//Current state of the traffic light
	private LightState currState;
	//Time the traffic light is going to be open
	private int greenTime;
	//Time the traffic light is going to be close
	private int redTime;
	private int count;
	//The direction regulated by the traffic light 
	private Direction direction;
	
	//Constructor
	public TrafficLight(int greenTime, int redTime, LightState state, Direction direction) {
		this.componentType = componentType.TRAFFIC_LIGHT;
		this.greenTime = greenTime;
		this.redTime = redTime;
		this.currState = state;
		this.count = 0;
		this.direction = direction;
	}
	
	public boolean isGreen() {
		return currState.equals(LightState.GREEN);
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	//Method that controls the traffic light cycle
	@ScheduledMethod(start=1.0, interval=1)
	public void update() {
		//Each tick increases the count on 1, when it reaches the greenTime or redTime changes its colour
		count++;
		switch (currState) {
		 case GREEN:
			 if(count>=greenTime) {
				 currState = LightState.RED;
				 count = 0;
			 }
			 break;
		 case RED:
			 if(count>=redTime) {
				 currState = LightState.GREEN;
				 count = 0;
			 }
			 break;
		}
	}
}
