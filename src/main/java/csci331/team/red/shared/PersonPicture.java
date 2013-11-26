package csci331.team.red.shared;

import java.util.Random;

import java.util.ArrayList;

/**
 * Enum to describe all art assets, linked with {@link Face}.
 * 
 * @author ojourmel
 * @author melany
 * @author Lduperron
 */
public enum PersonPicture {
	MALE1(Face.MALE1, Gender.MALE, true), INTROFEMALE1(Face.INTROFEMALE1,
			Gender.FEMALE, false), THUG1(Face.THUG1, Gender.MALE, false);

	public final Face f;
	public final Gender g;

	// Do not allow boss, or intro PP's to be in general circulation
	private final boolean regular;

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	private PersonPicture() {
		f = null;
		g = null;
		regular = false;
	}

	private PersonPicture(Face f, Gender g, boolean regular) {
		this.f = f;
		this.g = g;
		this.regular = regular;
	}

	private static final Random RANDOM = new Random();

	public static PersonPicture getRandom(Gender g) {

		ArrayList<PersonPicture> rightGender = new ArrayList<PersonPicture>();
		PersonPicture[] arr = values();

		for (PersonPicture pp : arr) {
			if (pp.g == g && pp.regular) {
				rightGender.add(pp);
			}
		}
		if (!rightGender.isEmpty()) {

			return rightGender.get(RANDOM.nextInt(rightGender.size()));
		} else {
			if (g != null) {
				throw new RuntimeException("Invalid Gender " + g.name());
			} else {
				throw new RuntimeException("Null Gender");
			}
		}
	}
}
