package trabajo;

import trabajo.GlobalConstants.Direction;

public class Crossroad extends NetworkTile {
	private Direction[] possibleDirections;
	
	public Crossroad(Direction[] possibleDirections) {
		super();
		this.componentType = ComponentType.CROSSROAD;
		this.possibleDirections = possibleDirections;
	}
	
	public Direction[] getPossibleDirections() {
		return this.possibleDirections;
	}
		
}
