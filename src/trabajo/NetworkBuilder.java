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
		 10 , 10);
		 
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
	                        new SimpleGridAdder<NetworkComponent>(), true, 10, 10
	                )
	        );
	     
	     createRoads();
	     createEndpoints();
		 createTrafficLights();
		 
		 return context ;
	}
	
	private void createRoads() {
		//Create the roads
		 NetworkComponent road;
		 for (int i=0; i<10; i++) {
			 for(int j=0; j<10; j++) {
				 if(i==5&&j==5) {
					 road = new Crossroad(new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT});
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
		endpoint = new Endpoint(Direction.UP);
		Utils.addComponent(endpoint, new NdPoint(5,0));
		endpoint = new Endpoint(Direction.RIGHT);
		Utils.addComponent(endpoint, new NdPoint(0,5));
	}
	
	private void createTrafficLights() {
		//Create the traffic lights
		NetworkComponent trafficLight;
		trafficLight = new TrafficLight(8, 8, LightState.GREEN, Direction.UP);
		Utils.addComponent(trafficLight, new NdPoint(5,4));
		trafficLight = new TrafficLight(8, 8, LightState.RED, Direction.RIGHT);
		Utils.addComponent(trafficLight, new NdPoint(4,5));
	}

}
