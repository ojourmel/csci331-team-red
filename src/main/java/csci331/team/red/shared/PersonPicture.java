package csci331.team.red.shared;

import java.util.ArrayList;

/**
 * TODO: JavaDoc
 * 
 */
public enum PersonPicture {
	MALE1(Face.MALE1, Gender.MALE), FEMALE1(Face.FEMALE1, Gender.FEMALE), THUG1(
			Face.THUG1, Gender.MALE);

	public final Face f;
	public final Gender g;

	private PersonPicture(Face f, Gender g) {
		this.f = f;
		this.g = g;
	}

	public static PersonPicture getRandom(Gender g) {

		ArrayList<PersonPicture> rightGender = new ArrayList<PersonPicture>();
		PersonPicture[] arr = values();

		for (PersonPicture pp : arr) {
			if (pp.g == g) {
				rightGender.add(pp);
			}
		}
		if (!rightGender.isEmpty()) {
			return rightGender.get((int) Math.random() * (arr.length));
		} else {
			throw new RuntimeException("Invalid Gender " + g.name());
		}
	}
}