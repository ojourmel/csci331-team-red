package csci331.team.red.shared;

/**
 * Java implementation of a game level.<br>
 * 
 * Contains several static methods which return hardcoded instances of
 * {@link Level}<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 * 
 */
public class Level {

	private Background database;
	private Background interactive;
	private SoundTrack soundTrack;

	/**
	 * @return a "wait" {@link Level} object
	 */
	public static Level getWait() {
		Level wait = new Level();
		wait.database = Background.WAITING;
		wait.interactive = Background.WAITING;

		wait.soundTrack = SoundTrack.WHISTLING;

		return wait;
	}

	/**
	 * @return a "campus" {@link Level} object
	 */
	public static Level getCampus() {
		Level campus = new Level();

		campus.database = Background.CAMPUS;
		campus.interactive = Background.CAMPUS_WORKSPACE;

		campus.soundTrack = SoundTrack.CONTRY_SONG;
		return campus;
	}

	/**
	 * @return a "roadside" {@link Level} object
	 */
	public static Level getRoadside() {
		Level road = new Level();

		road.database = Background.COP_CAR;
		road.interactive = Background.ROAD_SIDE;

		road.soundTrack = SoundTrack.ROCK_SONG;
		return road;
	}

	/**
	 * @return a "space" {@link Level} object
	 */
	public static Level getSpace() {
		Level space = new Level();

		space.database = Background.OFFICE_WORKSPACE;
		space.interactive = Background.SPACE_SIDE;

		space.soundTrack = SoundTrack.SPACE_SONG;
		return space;
	}
}
