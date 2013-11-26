package csci331.team.red.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Gender;
import csci331.team.red.shared.PersonPicture;

/**
 * CSCI331-TAG MW INTERFACE<br>
 * 
 * The CharacterRepository Class provides an interface for game play. It is the
 * only class that other members use to interact with the database and the
 * current game play character database<br>
 * <br>
 * 
 * CSCI331-TAG MW SUBCLASS<br>
 * 
 * The CharacterRepository Class extends from the CharacterDAO class as it uses
 * the static global variables instantiated in CharacterDAO<br>
 * <br>
 * 
 * @author melany
 * 
 */
public class CharacterRepository extends CharacterDAO {

	ArrayList<Character> characters;
	private static final int MIN_RESULT = 10;

	/**
	 * @author melany
	 */
	public CharacterRepository() {
		characters = new ArrayList<Character>();
	}

	/**
	 * CSCI331-TAG MW OVERRIDING <br>
	 * <br>
	 * 
	 * Returns a random character and persists it in the game play character
	 * database.<br>
	 * 
	 * @return Character
	 * @author melany
	 */
	@Override
	public Character getCharacter() {
		Character character = super.getCharacter();
		characters.add(character);
		return character;
	}

	/**
	 * Iterates through the game-play characters looking for characters that
	 * match the <key,value> pair supplied in the parameter. All matches are
	 * added to the return list of characters.<br>
	 * <br>
	 * If there are no characters in the game-play database that have the
	 * required parameters, new characters will be created that have the
	 * parameter, added to the character list AND to the game play database and
	 * returned.
	 * 
	 * @param attributes
	 * @return Set of Characters, of size at least 10
	 */
	public Set<Character> getCharacters(Map<String, String> attributes) {

		if (attributes.isEmpty()) {
			throw new RuntimeException("Empty Attribute Map");
		}

		Map<String, List<Character>> singleMatches = new HashMap<String, List<Character>>();

		// Go through and see if the key is a database attribute
		// If found, we add that character to the chars list
		for (String type : attributes.keySet()) {
			String val = attributes.get(type);

			if (type.equals(FIRSTNAME)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}

				for (Character character : characters) {
					if (character.getFirstName().equalsIgnoreCase(val)) {
						l.add(character);
					}
				}

			} else if (type.equals(LASTNAME)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getLastName().equalsIgnoreCase(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(DOB)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getDob().equalsIgnoreCase(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(DRIVERSID)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				// Search for "contains" in Drivers ID
				for (Character character : characters) {
					if (character.getDriversID().contains(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(PASSPORTID)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				// Search for "contains" in Passport ID
				for (Character character : characters) {
					if (character.getPassportID().contains(val)) {
						l.add(character);
					}
				}

			} else if (type.equals(OCCUPATION)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getOccupation().equalsIgnoreCase(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(ADDRESS)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getAddress().contains(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(CITY)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getCity().equalsIgnoreCase(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(REGION)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getRegion().equalsIgnoreCase(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(POSTAL)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getPostal().contains(val)) {
						l.add(character);
					}
				}
			} else if (type.equals(COUNTRY)) {
				List<Character> l = singleMatches.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					singleMatches.put(type, l);
				}
				for (Character character : characters) {
					if (character.getCountry().equalsIgnoreCase(val)) {
						l.add(character);
					}
				}
			} else {
				// ERROR, attribute key that was not recognized. This is a
				// programmers error, and thus, end the program.
				throw new RuntimeException("Invalid Character Attribute "
						+ type);
			}

		}

		Set<Character> matchesAll = new HashSet<Character>();

		// Now create a master list of people that match ALL of the given
		// attributes

		// We need access to specific keys, so copy them into a list
		ArrayList<String> types = new ArrayList<String>();
		types.addAll(attributes.keySet());

		// Compare lists of characters with other lists of characters
		// looking for duplicates for matches. If there was only one attribute
		// searched for, then return the full list
		if (types.size() == 1) {
			matchesAll.addAll(singleMatches.get(types.get(0)));
		} else {
			for (int h = 0; h < (types.size()); h++) {
				List<Character> temp1 = singleMatches.get(types.get(h));
				for (int i = 0; i < (types.size()); i++) {
					if (h != i) {

						List<Character> temp2 = singleMatches.get(types.get(i));

						for (int j = 0; j < (temp1.size()); j++) {
							Character t1 = temp1.get(j);
							for (int k = 0; k < (temp2.size()); k++) {
								Character t2 = temp2.get(k);
								if (t1.equals(t2)) {
									matchesAll.add(t1);
								}
							}
						}
					}
				}
			}
		}

		// If chars is empty, create some terms with the specified data for the
		// game play database

		while (matchesAll.size() < MIN_RESULT) {

			Character temp = super.getCharacter();
			// change the random data to the specific data
			// That way it will LOOK like data was found
			for (String type : attributes.keySet()) {
				String val = attributes.get(type);
				if (isValid(type, val)) {

					if (type.equals(FIRSTNAME)) {
						temp.setFirstName(val);
					} else if (type.equals(LASTNAME)) {
						temp.setLastName(val);
					} else if (type.equals(DOB)) {
						temp.setDob(val);
					} else if (type.equals(DRIVERSID)) {
						temp.setDriversID(val);
					} else if (type.equals(PASSPORTID)) {
						temp.setPassportID(val);
					} else if (type.equals(OCCUPATION)) {
						temp.setOccupation(val);
					} else if (type.equals(ADDRESS)) {
						temp.setAddress(val);
					} else if (type.equals(CITY)) {
						temp.setCity(val);
					} else if (type.equals(REGION)) {
						temp.setRegion(val);
					} else if (type.equals(POSTAL)) {
						temp.setPostal(val);
					} else if (type.equals(COUNTRY)) {
						temp.setCountry(val);
					} else {
						// ERROR, attribute key that was not recognized. This is
						// a programmers error, and thus, end the program.
						throw new RuntimeException(
								"Invalid Character Attribute " + type);
					}
				} else {

					return matchesAll;

				}
			}
			// add temp to the master list, and the matches list.
			matchesAll.add(temp);
			characters.add(temp);
		}

		return matchesAll;
	}

	/**
	 * @author ojourmel
	 */
	public Character getIntroCharacter() {
		Character intro = null;

		String dob = "1990-02-23";
		String firstName = "Eliza";
		String lastName = "Ehlers";
		String driversID = getDriversID();
		String passportID = getPassportID(randomID(PASSPORTID));
		String address = getAddress(randomID(ADDRESS));
		String city = "Omianan";
		String region = "Aibmuloc Hsitirb";
		String postal = getPostal(randomID(POSTAL));
		String country = "Adanac";
		String occupation = "University Student";
		Gender gender = Gender.FEMALE;
		PersonPicture avatar = PersonPicture.INTROFEMALE0;

		intro = new Character(dob, driversID, firstName, lastName, passportID,
				address, city, region, postal, country, occupation, gender,
				avatar);

		intro.setFraud(false);
		
		characters.add(intro);

		return intro;
	}

	/**
	 * @author ojourmel
	 */
	public Character getBossCharacter(Boss boss) {
		Character bossCharacter = null;
		switch (boss) {
		case THUGLIFE:

			String dob = "1991-04-20";
			String firstName = "icE";
			String lastName = "cOld";
			String driversID = getDriversID();
			String passportID = getPassportID(randomID(PASSPORTID));
			String address = getAddress(randomID(ADDRESS));
			String city = getCity(randomID(CITY));
			String region = getRegion(randomID(REGION));
			String postal = getPostal(randomID(POSTAL));
			String country = "'Merica";
			String occupation = "Pro-Bono Video Game Tester";
			Gender gender = Gender.MALE;
			PersonPicture avatar = PersonPicture.THUG1;

			bossCharacter = new Character(dob, driversID, firstName, lastName,
					passportID, address, city, region, postal, country,
					occupation, gender, avatar);

		}
		
		characters.add(bossCharacter);

		return bossCharacter;
	}

	// TODO: Create proper JUnit testing using this kind of testing.
	public static void main(String[] args) {

		CharacterRepository repo = new CharacterRepository();
		Character c = repo.getCharacter();
		System.out.println("INITAL Char:");
		System.out.println(c.toString());

		Map<String, String> map = new HashMap<String, String>();
		map.put(FIRSTNAME, c.getFirstName());
		map.put(DOB, "1980-Jun-31");
		Set<Character> l = repo.getCharacters(map);

		System.out.println("\nChars found in search query:\n");
		System.err.println(l.size());

		for (Character chara : l) {
			System.out.println(chara.toString());
		}

	}
}