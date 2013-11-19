package csci331.team.red.shared;

/**
 * TODO: Write me
 * 
 * @author melany
 */
public enum Gender {

	MALE, FEMALE;

	public static Gender fromChar(char c) {
		Gender g = FEMALE;
		switch (c) {
		case 'M':
			g = MALE;
			break;
		case 'F':
			g = FEMALE;
			break;
		default:
		}
		return g;
	}

}