package csci331.team.red.client;



public enum DatabaseBoundingBoxes
{

	
	LEVELONE (BoundingBoxes.LevelOneAlertDisplayBoundingBox, BoundingBoxes.LevelOneComputerDisplayBoundingBox , BoundingBoxes.LevelOneComputerEntryBoundingBox);
	
	
	public enum BoundingBoxes
	{
		// Kinda arbitary, but they work for the resolution we've chosen / how it lines up with the background
		LevelOneAlertDisplayBoundingBox (10, 100, 250, 350),
		LevelOneComputerDisplayBoundingBox(410, 50, 240, 440),
		LevelOneComputerEntryBoundingBox(410, 0, 240, 47);
	
		public float x;
		public float y;
		public float width;
		public float height;
		
		private BoundingBoxes(float x, float y, float width, float height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
	
	public BoundingBoxes AlertDisplayBoundingBox;
	public BoundingBoxes ComputerDisplayBoundingBox;
	public BoundingBoxes ComputerEntryBoundingBox;

	
	DatabaseBoundingBoxes(BoundingBoxes alertDisplay, BoundingBoxes computerDisplay, BoundingBoxes computerEntry)
	{
		this.AlertDisplayBoundingBox = alertDisplay;
		this.ComputerDisplayBoundingBox = computerDisplay;
		this.ComputerEntryBoundingBox = computerEntry;
		
	}
	
}
