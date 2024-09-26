package trabajo;

import repast.simphony.engine.schedule.ScheduledMethod;
import trabajo.GlobalConstants.Direction;
import trabajo.GlobalConstants.LightState;

public class TrafficLightOffset extends TrafficLight{
		
		private int startOffset;
		private boolean started;
		
		//Constructor
		public TrafficLightOffset(int greenTime, int redTime, LightState state, Direction direction, int startOffset) {
			this.componentType = componentType.TRAFFIC_LIGHT;
			this.greenTime = greenTime;
			this.redTime = redTime;
			this.currState = state;
			this.count = 0;
			this.direction = direction;
			this.startOffset = startOffset;
			if(startOffset==0) {
				started = true;
			} else {
				started = false;
			}
		}
		
		//Method that controls the traffic light cycle
		@ScheduledMethod(start=1.0, interval=1)
		public void update() {
			//Each tick increases the count on 1, when it reaches the greenTime or redTime changes its colour
			this.count++;
			if(!this.started) {
				if(this.count>=this.startOffset) {
					this.started = true;
					this.count=0;
					this.currState = LightState.GREEN;
				}
			}else {
				switch (currState) {
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
}
