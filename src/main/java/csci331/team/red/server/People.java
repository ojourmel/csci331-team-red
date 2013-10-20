package csci331.team.red.server;

import java.util.HashMap;
import java.util.Map;

import csci331.team.red.shared.Person;

/**
 * A wrapper class for all of the currently created, random people to be use for
 * this play through.<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class People {

	private Map<Integer, Person> people;

	public People() {
		// generate some random people.
		// Hard coded for now.
		people = new HashMap<Integer, Person>();
		people.put(2314, new Person());
	}

	public Person getRandom() {
		return null;
	}

	public Person getLevelBoss(int i) {
		switch (i) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		default:
			// Tried to get a boss from a level that does not exist!!
			break;
		}

		return null;
	}
}
