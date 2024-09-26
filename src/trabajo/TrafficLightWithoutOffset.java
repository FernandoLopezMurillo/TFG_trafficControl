package trabajo;

import repast.simphony.engine.schedule.ScheduledMethod;
import trabajo.GlobalConstants.*;

public class TrafficLightWithoutOffset extends TrafficLight{
	
	
	//Constructor
	public TrafficLightWithoutOffset(int greenTime, int redTime, LightState state, Direction direction) {
		this.componentType = componentType.TRAFFIC_LIGHT;
		this.greenTime = greenTime;
		this.redTime = redTime;
		this.currState = state;
		this.count = 0;
		this.direction = direction;
	}
	
	
	//Method that controls the traffic light cycle
	@ScheduledMethod(start=1.0, interval=1)
	public void update() {
		//Each tick increases the count on 1, when it reaches the greenTime or redTime changes its colour
		this.count++;
		switch (this.currState) {
		 case GREEN:
			 if(this.count>=this.greenTime) {
				 this.currState = LightState.RED;
				 this.count = 0;
			 }
			 break;
		 case RED:
			 if(this.count>=this.redTime) {
				 this.currState = LightState.GREEN;
				 this.count = 0;
			 }
			 break;
		}
	}
}
