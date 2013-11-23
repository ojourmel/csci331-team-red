package csci331.team.red.shared;

import java.lang.Character;

/**
 * @author melany
 */
public enum Gender {

	MALE, FEMALE;

	public static Gender fromChar(char c) {
		c = Character.toUpperCase(c);
		Gender g = FEMALE;
		switch (c) {
		case 'M':
			g = MALE;
			break;
		case 'F':
			g = FEMALE;
			break;
		default:
			g = null;
		}
		return g;
	}
}