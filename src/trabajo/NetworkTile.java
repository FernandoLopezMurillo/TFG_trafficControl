package trabajo;

public class NetworkTile extends NetworkComponent{

	public enum TileType {ROAD, ENDPOINT, BLOCK, CROSSROAD};

	protected TileType tile_type;	
	public NetworkTile()
	{
		componentType = ComponentType.TILE;
		this.tile_type = TileType.BLOCK;
	}

	public TileType getTileType() {
		return tile_type;
	}
}
