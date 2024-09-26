package trabajo;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import trabajo.GlobalConstants.Direction;
import trabajo.GlobalConstants.LightState;


public class NetworkBuilder implements ContextBuilder<NetworkComponent> {

	//Create the context
	@Override
	public Context build(Context<NetworkComponent> context) {
		 context.setId("TFG");
		 //Create the continuous space where the simulation is going to develop
		 ContinuousSpace<NetworkComponent> simSpace =
		 ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null).createContinuousSpace ("space", context ,
		 new SimpleCartesianAdder<NetworkComponent>() ,
		 new repast.simphony.space.continuous.StrictBorders() ,
		 GlobalConstants.SPACE_WIDTH , GlobalConstants.SPACE_HEIGHT);
		 
		 //Initializes the context and the space
		 try {
			Utils.init(context, simSpace);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 
		 //Create a grid so we can locate the objects in cells 
		 GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
	        Grid<NetworkComponent> grid = gridFactory.createGrid(
	                "grid", context, 
	                new GridBuilderParameters<NetworkComponent>(
	                        new StrictBorders(),
	                        new SimpleGridAdder<NetworkComponent>(), true, GlobalConstants.SPACE_WIDTH, GlobalConstants.SPACE_HEIGHT
	                )
	        );
	     
	     createRoads();
	     createEndpoints();
		 createTrafficLights();
		 
	     
		 /*createRoads2();
		 createEndpoints2();
		 createTrafficLights2();
		 */
		 return context ;
	}
	
	private void createRoads() {
		//Create the roads
		 NetworkComponent road;
		 for (int i=0; i<11; i++) {
			 for(int j=0; j<11; j++) {
				 if(i==5&&j==5) {
					 road = new Crossroad(new Direction[] {Direction.UP, Direction.RIGHT});
					 Utils.addComponent(road, i, j);
				 } else if(i==5 || j==5) {
					 road = new Road();
					 Utils.addComponent(road, i, j);
				 }
			 }
		 }
	}
	
	private void createEndpoints() {
		//Create the endpoints
		NetworkComponent endpoint;
		endpoint = new Endpoint(Direction.UP,3);
		Utils.addComponent(endpoint, new NdPoint(5,0));
		endpoint = new Endpoint(Direction.RIGHT,5);
		Utils.addComponent(endpoint, new NdPoint(0,5));
	}
	
	private void createTrafficLights() {
		//Create the traffic lights
		NetworkComponent trafficLight;
		trafficLight = new TrafficLightWithoutOffset(8, 8, LightState.GREEN, Direction.UP);
		Utils.addComponent(trafficLight, new NdPoint(5,4));
		trafficLight = new TrafficLightWithoutOffset(8, 8, LightState.RED, Direction.RIGHT);
		Utils.addComponent(trafficLight, new NdPoint(4,5));
	}
	
	private void createRoads2() {
		//Create the roads
		 NetworkComponent road;
		 for (int i=0; i<GlobalConstants.SPACE_WIDTH; i++) {
			 for(int j=0; j<GlobalConstants.SPACE_HEIGHT; j++) {
				 if(i==4&&j==4) {
					 road = new Crossroad(new Direction[] {Direction.DOWN, Direction.RIGHT});
					 Utils.addComponent(road, i, j);
				 }else if(i==6&&j==4) {
					 road = new Crossroad(new Direction[] {Direction.UP, Direction.RIGHT});
					 Utils.addComponent(road, i, j);
				 }else if(i==6&&j==6) {
					 road = new Crossroad(new Direction[] {Direction.UP, Direction.LEFT});
					 Utils.addComponent(road, i, j);
				 }else if(i==4&&j==6) {
					 road = new Crossroad(new Direction[] {Direction.DOWN, Direction.LEFT});
					 Utils.addComponent(road, i, j);
				 } else if(i==6||j==6||i==4||j==4) {
					 road = new Road();
					 Utils.addComponent(road, i, j);
				 }
			 }
		 }
	}
	
	private void createEndpoints2() {
		//Create the endpoints
		NetworkComponent endpoint;
		endpoint = new Endpoint(Direction.UP,3);
		Utils.addComponent(endpoint, new NdPoint(6,0));
		endpoint = new Endpoint(Direction.RIGHT,5);
		Utils.addComponent(endpoint, new NdPoint(0,4));
		endpoint = new Endpoint(Direction.DOWN,2);
		Utils.addComponent(endpoint, new NdPoint(4,10));
		endpoint = new Endpoint(Direction.LEFT,10);
		Utils.addComponent(endpoint, new NdPoint(10,6));
	}
	
	private void createTrafficLights2() {
		//Create the traffic lights
		NetworkComponent trafficLight;
		trafficLight = new TrafficLightOffset(8, 16, LightState.GREEN, Direction.UP, 0);
		Utils.addComponent(trafficLight, new NdPoint(6,3));
		trafficLight = new TrafficLightOffset(8, 16, LightState.RED, Direction.RIGHT, 8);
		Utils.addComponent(trafficLight, new NdPoint(3,4));
		trafficLight = new TrafficLightOffset(8, 16, LightState.RED, Direction.DOWN, 16);
		Utils.addComponent(trafficLight, new NdPoint(4,7));
		trafficLight = new TrafficLightOffset(8, 16, LightState.RED, Direction.LEFT, 8);
		Utils.addComponent(trafficLight, new NdPoint(7,6));
	}
}
