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
		wait.database = Background.WAITING;
		wait.interactive = Background.WAITING;

		wait.soundTrack = SoundTrack.WHISTLING;

		return wait;
	}

	public static Level getCampus() {
		Level campus = new Level();

		campus.database = Background.CAMPUS;
		campus.interactive = Background.CAMPUS_WORKSPACE;

		campus.soundTrack = SoundTrack.CONTRY_SONG;
		return campus;
	}

	public static Level getRoadside() {
		Level road = new Level();

		road.database = Background.COP_CAR;
		road.interactive = Background.ROAD_SIDE;

		road.soundTrack = SoundTrack.ROCK_SONG;
		return road;
	}

	public static Level getSpace() {
		Level space = new Level();

		space.database = Background.OFFICE_WORKSPACE;
		space.interactive = Background.SPACE_SIDE;

		space.soundTrack = SoundTrack.SPACE_SONG;
		return space;
	}
}
