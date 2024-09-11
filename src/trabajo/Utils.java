package trabajo;


import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import trabajo.GlobalConstants.Direction;

public class Utils {
	private static Context<NetworkComponent> simContext;
	private static ContinuousSpace<NetworkComponent> simSpace;
	private static boolean initialized = false;
	
	//Initialize the context and the space
	public static void init(Context<NetworkComponent> context, ContinuousSpace<NetworkComponent> space) throws Exception {
		if(context != null && space!=null && !initialized) {
			simContext = context;
			simSpace = space;
			initialized = true;
		} else {
			throw new Exception("Context or space null");
		}
	}
	
	//Return the coordinates of a component inside the space
	public static NdPoint getCoordinatesOf(NetworkComponent component) {
		if(component == null) {
			return null;
		}
		return simSpace.getLocation(component);
	}
	
	//Return all the components in that position 
	public static Iterable<NetworkComponent> getObjectsAt(NdPoint position){
		return getObjectsAt(position.getX(), position.getY());
	}
	
	//Return all the components in that position given by the parameters x and y
	public static Iterable<NetworkComponent> getObjectsAt(double x, double y) {
		return simSpace.getObjectsAt(x,y);
	}
	
	//Add a component in that position 
	public static void addComponent (NetworkComponent component, NdPoint position) {
		if(position != null) {
			addComponent(component, position.getX(), position.getY());
		} else {
			throw new IllegalArgumentException("Invalid position");
		}
	}
	
	//Add a component in that position given by the parameters x and y
	public static void addComponent(NetworkComponent component, double x, double y) {
		simContext.add(component);
		simSpace.moveTo(component, x, y);
	}
	
	//Return the position it will move to if follows that direction 
	public static NdPoint getFuturePosition(NdPoint pos, Direction dir) {
		double x = pos.getX();
		double y = pos.getY();
		switch(dir) {
			case UP: y += 1; break;
			case DOWN: y -= 1; break;
			case RIGHT: x += 1; break;
			case LEFT: x -= 1; break;
		}
		return new NdPoint(x,y);
	}
	
	//Move the component to that position
	public static void moveTo(NetworkComponent component, NdPoint pos) {
		moveTo(component, pos.getX(), pos.getY());
	}
	
	//Move the component to that position given by the parameters x and y
	public static void moveTo(NetworkComponent component, double x, double y) {
		simSpace.moveTo(component, x, y);
	}
	
	//Remove the component
	public static void remove(NetworkComponent component) {
		simContext.remove(component);
	}
	
}
