package csci331.team.red.shared;

/**
 * Java implementation of a game level.<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 * 
 */
public class Level {

	private Background database;
	private Background interactive;
	private SoundTrack soundTrack;

	public static Level getWait() {
		Level wait = new Level();
		wait.setDatabase(Background.WAITING);
		wait.setInteractive(Background.WAITING);

		wait.setSoundTrack(SoundTrack.SONG);

		return wait;
	}

	public static Level getCampus() {
		Level campus = new Level();

		campus.setDatabase(Background.LEVEL1DATABASEBG);
		campus.setInteractive(Background.LEVEL1FIELDBG);

		campus.setSoundTrack(SoundTrack.SONG);
		return campus;
	}

	public static Level getRoadside() {
		Level road = new Level();

		road.setDatabase(Background.LEVEL2DATABASEBG);
		road.setInteractive(Background.LEVEL2FIELDBG);

		road.setSoundTrack(SoundTrack.SONG);
		return road;
	}

	public static Level getSpace() {
		Level space = new Level();

		space.setDatabase(Background.LEVEL3DATABASEBG);
		space.setInteractive(Background.LEVEL3FIELDBG);

		space.setSoundTrack(SoundTrack.SONG);
		return space;
	}

	public Background getDatabase()
	{
		return database;
	}

	public void setDatabase(Background database)
	{
		this.database = database;
	}

	public SoundTrack getSoundTrack()
	{
		return soundTrack;
	}

	public void setSoundTrack(SoundTrack soundTrack)
	{
		this.soundTrack = soundTrack;
	}

	public Background getInteractive()
	{
		return interactive;
	}

	public void setInteractive(Background interactive)
	{
		this.interactive = interactive;
	}
}
