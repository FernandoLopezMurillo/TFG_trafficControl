package trabajo;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.NdPoint;
import trabajo.GlobalConstants.*;

public class Endpoint extends NetworkTile{
	private NdPoint position;
	private Direction carDirection;
	private double interval;
	
	public Endpoint() {
		super();
		this.tile_type = tile_type.ENDPOINT;
		this.position = Utils.getCoordinatesOf(this);
	}
	
	public Endpoint(Direction carDirection, double interval) {
		this();
		this.carDirection = carDirection;
		this.interval = interval;
		scheduleSpawn();
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
	
	//Instead of doing a ScheduledMethod where all Endpoints share the same interval for cars creation,
	//this method allows each Endpoint to have its own interval specified by the user 
	private void scheduleSpawn() {
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		//The value 5 represents the tick where the scheduled method starts, this can also be specified by the user
		ScheduleParameters params = ScheduleParameters.createRepeating(5.0, this.interval);
		schedule.schedule(params,this, "spawn");
	}
	
	//Method to create cars
	public void spawn() {
		this.position = Utils.getCoordinatesOf(this);
		if(!isTileBlocked()) {
			Car newCar = new Car(this.carDirection);
			Utils.addComponent(newCar, this.position);
		}
	}
	
}
