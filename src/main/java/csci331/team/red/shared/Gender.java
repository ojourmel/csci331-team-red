package csci331.team.red.shared;

import java.lang.Character;

/**
 * @author melany
 */
public enum Gender {

	MALE, FEMALE;

	/**
	 * @param c
	 * @return the {@link Gender} equivalent of the <b>c</b>
	 */
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

	/**
	 * @return the single character string equivalent of <b>this</b>
	 */
	@Override
	public String toString() {
		String s = "F";
		switch (this) {
		case MALE:
			s = "M";
			break;
		case FEMALE:
			s = "F";
			break;
		}
		return s;
	}


	/**
	 * @return the full string equivalent of <b>this</b>
	 */
	public String toFull() {

		String s = "Female";
		switch (this) {
		case MALE:
			s = "Male";
			break;
		case FEMALE:
			s = "Female";
			break;
		}
		return s;
	}
}