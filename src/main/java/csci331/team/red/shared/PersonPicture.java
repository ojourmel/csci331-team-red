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
	INTROFEMALE0(Face.INTROFEMALE0,Gender.FEMALE, false),
	THUG1(Face.THUG1, Gender.MALE, false),
	
	FEMALE1(Face.FEMALE1,Gender.FEMALE,true),
	FEMALE2(Face.FEMALE2,Gender.FEMALE,true),
	FEMALE3(Face.FEMALE3,Gender.FEMALE,true),
	FEMALE4(Face.FEMALE4,Gender.FEMALE,true),
	FEMALE5(Face.FEMALE5,Gender.FEMALE,true),
	FEMALE6(Face.FEMALE6,Gender.FEMALE,true),
	FEMALE7(Face.FEMALE7,Gender.FEMALE,true),
	FEMALE8(Face.FEMALE8,Gender.FEMALE,true),
	FEMALE9(Face.FEMALE9,Gender.FEMALE,true),
	
	MALE1(Face.MALE1,Gender.MALE,true),
	MALE2(Face.MALE2,Gender.MALE,true),
	MALE3(Face.MALE3,Gender.MALE,true),
	MALE4(Face.MALE4,Gender.MALE,true),
	MALE5(Face.MALE5,Gender.MALE,true),
	MALE6(Face.MALE6,Gender.MALE,true);
	

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
