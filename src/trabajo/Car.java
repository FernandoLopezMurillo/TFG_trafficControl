package trabajo;

import java.util.Random;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.NdPoint;
import trabajo.GlobalConstants.*;

public class Car extends NetworkComponent{
	
	private CarState currState;
	
	//Current direction
	private Direction currDirection;

	
	//Constructor
	public Car(Direction carDirection) {
		this.currState = CarState.DRIVING;
		this.currDirection = carDirection;
		this.componentType = NetworkComponent.ComponentType.CAR;
	}
	
	public CarState getState() {
		return currState;
	}
	
	public Direction getDirection() {
		return this.currDirection;
	}
	
	public void setState(CarState state) {
		this.currState = state;
	}
	
	//Check if the car is driving
	public boolean isDriving() {
		return this.currState.equals(CarState.DRIVING);
	}
	
	//Check if the car is stop waiting
	public boolean isWaiting() {
		return this.currState.equals(CarState.WAITING);
	}
	
	//Return all the components the car has in front of its current position
	private Iterable<NetworkComponent> getComponentsInFront(){
		double x, y;
		NdPoint currPosition = Utils.getCoordinatesOf(this);
		y = currPosition.getY();
		x = currPosition.getX();
		switch(this.currDirection) {
			case UP: 
				y += 1;
			break;
			case DOWN: 
				y -= 1;
			break;
			case LEFT: 
				x -= 1;
			break;
			case RIGHT: 
				x += 1;
			break;
		}
		return Utils.getObjectsAt(x, y);
	}
	
	//Return all the component the car has in front of its current position when the position is given by the parameters x and y
	private Iterable<NetworkComponent> getComponentsInFrontOfPosition(double x, double y){
		switch(this.currDirection) {
			case UP: 
				y += 1;
			break;
			case DOWN: 
				y -= 1;
			break;
			case LEFT: 
				x -= 1;
			break;
			case RIGHT: 
				x += 1;
			break;
		}
		return Utils.getObjectsAt(x, y);
	}
	
	//Return all the possible directions when the car arrives to a crossroad
	private Direction[] getTurningDirections(Direction[] crossroadDirections) {
		Direction[] possibleDirections = new Direction[crossroadDirections.length -1];
		int index = 0;
		for(int i=0; i<crossroadDirections.length; i++) {
			Direction dir = crossroadDirections[i];
			switch (this.currDirection) {
				case UP:
					if(!dir.equals(Direction.DOWN)) {
						possibleDirections[index++] = dir;
					}
				break;
				case DOWN:
					if(!dir.equals(Direction.UP)) {
						possibleDirections[index++] = dir;
					}
				break;
				case LEFT:
					if(!dir.equals(Direction.RIGHT)) {
						possibleDirections[index++] = dir;
					}
				break;
				case RIGHT:
					if(!dir.equals(Direction.LEFT)) {
						possibleDirections[index++] = dir;
					}
				break;
			}
		}
		return possibleDirections;
	}
	
	//From all the possible directions pick one randomly and update the cars nextDirection
	private void pickNextDirection(Crossroad crossroad) {
		Direction[] possibleDirections = getTurningDirections(crossroad.getPossibleDirections());
		Random r = new Random();
		int rndIndex = r.nextInt(possibleDirections.length);
		this.currDirection = possibleDirections[rndIndex];
	}
	
	//Check if the next cell the car is going to move is inside the grid
	private boolean notOutOfBounds(NdPoint pos) {
		return notOutOfBounds(pos.getX(), pos.getY());
	}
	
	//Check if the next cell the car is going to move is inside the grid by providing the position with parameters x and y
	private boolean notOutOfBounds(double x, double y) {
		boolean notOutOfBounds = false;
		switch (this.currDirection) {
			case UP: notOutOfBounds = y < GlobalConstants.SPACE_HEIGHT - 1; break;
			case DOWN: notOutOfBounds = y > 0; break;
			case LEFT: notOutOfBounds = x > 0; break;
			case RIGHT: notOutOfBounds = x < GlobalConstants.SPACE_WIDTH - 1; break;
		}
		return notOutOfBounds;
	}
	
	//Method which simulates the car movement
	@ScheduledMethod(start=1.0, interval=1)
	public void step() {
		boolean allowedToMove = true;
		Crossroad crossroad = null;
		//Get all the objects in front of the car and check if it is a crossroad
		NdPoint currentPos = Utils.getCoordinatesOf(this);
		Iterable<NetworkComponent> objectsInFront = getComponentsInFront();
		for(NetworkComponent component : objectsInFront) {
			switch (component.getComponentType()) {
				//Check if the car has another car waiting in front, if so, allowedToMove becomes false
				case CAR:
					Car car = (Car) component;
					if(car.getState().equals(CarState.WAITING)&&
							car.getDirection().equals(this.currDirection)) {
						allowedToMove = false;
					}
					break;
					
				case CROSSROAD:
					crossroad = (Crossroad) component;
					break;
				//Check if the traffic light is open, if not, allowedToMove becomes false	
				case TRAFFIC_LIGHT:
					TrafficLight trafficLight = (TrafficLight) component;
					if(!trafficLight.isGreen()&& trafficLight.getDirection().equals(this.currDirection)){
						allowedToMove = false;
					}
					break;
				default:
					break;
			}
		}
		
		//Check if the car is allowed to move, if not, change its state to waiting
		if(allowedToMove) {
			setState(CarState.DRIVING);
			//Move the car to the next position if it is not out of bounds, if so, delete the car
			NdPoint newPos = Utils.getFuturePosition(currentPos, this.currDirection);
			if(notOutOfBounds(newPos)) {
				Utils.moveTo(this, newPos);
				if(crossroad!=null) {
					pickNextDirection(crossroad);
				} 
			} else {
				Utils.remove(this);
			}
		} else {
			setState(CarState.WAITING);
		}
	}
}
