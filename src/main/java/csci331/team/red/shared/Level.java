package csci331.team.red.shared;

import csci331.team.red.client.DatabaseBoundingBoxes;

/**
 * Java implementation of a game level.<br>
 * 
 * Contains several static methods which return hardcoded instances of
 * {@link Level}<br>
 * FIXME: <b>Stub</b>
 * This really should be an enum...
 * 
 * @author ojourmel
 * 
 */
public class Level {

	private String name;
	private Background database;
	private Background interactive;
	private SoundTrack soundTrack;
	
	private DatabaseBoundingBoxes bounds;

	
	/**
	 * @return a "wait" {@link Level} object
	 */
	public static Level getWait() {
		Level wait = new Level();
		wait.setDatabase(Background.WAITING);
		wait.setInteractive(Background.WAITING);
		wait.setSoundTrack(SoundTrack.SONG);
		

		wait.setName("Wait");

		return wait;
	}

	/**
	 * @return a "campus" {@link Level} object
	 */
	public static Level getCampus() {
		Level campus = new Level();

		campus.setDatabase(Background.LEVEL1DATABASEBG);
		campus.setBounds(DatabaseBoundingBoxes.LEVELONE);
		
		campus.setInteractive(Background.LEVEL1FIELDBG);

		campus.setSoundTrack(SoundTrack.SONG);

		campus.setName("Campus");

		return campus;
	}

	/**
	 * @return a "roadside" {@link Level} object
	 */
	public static Level getRoadside() {
		Level road = new Level();

		road.setDatabase(Background.LEVEL2DATABASEBG);
		road.setInteractive(Background.LEVEL2FIELDBG);

		road.setSoundTrack(SoundTrack.SONG);

		road.setName("Road");

		return road;
	}

	/**
	 * @return a "space" {@link Level} object
	 */
	public static Level getSpace() {
		Level space = new Level();

		space.setDatabase(Background.LEVEL3DATABASEBG);
		space.setInteractive(Background.LEVEL3FIELDBG);

		space.setSoundTrack(SoundTrack.SONG);

		space.setName("Space");
		return space;
	}

	public Background getDatabase() {
		return database;
	}

	public void setDatabase(Background database) {
		this.database = database;
	}

	public SoundTrack getSoundTrack() {
		return soundTrack;
	}

	public void setSoundTrack(SoundTrack soundTrack) {
		this.soundTrack = soundTrack;
	}

	public Background getInteractive() {
		return interactive;
	}

	public void setInteractive(Background interactive) {
		this.interactive = interactive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DatabaseBoundingBoxes getBounds()
	{
		return bounds;
	}

	public void setBounds(DatabaseBoundingBoxes bounds)
	{
		this.bounds = bounds;
	}
}