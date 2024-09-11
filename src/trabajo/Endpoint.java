package trabajo;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.NdPoint;
import trabajo.GlobalConstants.*;

public class Endpoint extends NetworkTile{
	private NdPoint position;
	private Direction carDirection;
	
	public Endpoint() {
		super();
		this.tile_type = tile_type.ENDPOINT;
		this.position = Utils.getCoordinatesOf(this);
	}
	
	public Endpoint(Direction carDirection) {
		this();
		this.carDirection = carDirection;
	}
	
	//Check if the endpoint is currently blocked by another car
	private boolean isTileBlocked() {
		boolean blocked;
		this.position = Utils.getCoordinatesOf(this);
		Iterable<NetworkComponent> objects = Utils.getObjectsAt(position);
		blocked = false;
		for(NetworkComponent component : objects) {
			if(component.getComponentType() == ComponentType.CAR) {
				Car car = (Car) component;
				blocked = car.getDirection() == this.carDirection ? true : false;
			}
		}
		return blocked;
	}
	
	//Method to create cars
	@ScheduledMethod(start= 5.0, interval = 6)
	public void spawn() {
		this.position = Utils.getCoordinatesOf(this);
		if(!isTileBlocked()) {
			Car newCar = new Car(this.carDirection);
			Utils.addComponent(newCar, this.position);
		}
	}
	
}
