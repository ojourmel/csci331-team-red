package csci331.team.red.dao;

//import static csci331.team.red.dao.CharacterDAO.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private static final int INITIAL_CHARACTERS = 2500;
	private static final int NEW_CHARACTERS = 10;

	/**
	 * The construction creates an initial game-play character database of 50
	 * random characters
	 * 
	 * @author melany
	 */
	CharacterRepository() {
		characters = new ArrayList<Character>();

		// Create the first 50 characters for game play
		for (int i = 0; i < INITIAL_CHARACTERS; i++) {
			characters.add(super.getCharacter());
		}
	}

	/**
	 * FIXME: This will only ever return the same 50 people, even if it is
	 * called 9001 times... Perhaps, "remember" who has been called. ie. do lazy
	 * initilization??
	 * 
	 * 
	 * CSCI331-TAG MW OVERRIDING <br>
	 * <br>
	 * 
	 * Returns a random character in the game play character database.<br>
	 * This is different from the CharacterDAO.getCharacter, which creates a
	 * random character
	 * 
	 * @return Character
	 * @author melany
	 */
	@Override
	public Character getCharacter() {
		int range = (int) (Math.random() * (characters.size()));
		return characters.get(range);
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
	 * @return List of Characters, returns null on error
	 */
	public List<Character> getCharacters(Map<String, String> attributes) {

		if (attributes.isEmpty()) {
			throw new RuntimeException("Empty Attribute Map");
		}

		Map<String, List<Character>> matchesAny = new HashMap<String, List<Character>>();

		// Go through and see if the key is a database attribute
		// If found, we add that character to the chars list
		for (String type : attributes.keySet()) {
			String val = attributes.get(type);

			if (type == FIRSTNAME) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}

				for (Character character : characters) {
					if (character.getFirstName() == val) {
						l.add(character);
					}
				}

			} else if (type == LASTNAME) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getLastName() == val) {
						l.add(character);
					}
				}
			} else if (type == DOB) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getDob() == val) {
						l.add(character);
					}
				}
			} else if (type == DRIVERSID) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getDriversID() == val) {
						l.add(character);
					}
				}
			} else if (type == PASSPORTID) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getPassportID() == val) {
						l.add(character);
					}
				}

			} else if (type == OCCUPATION) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getOccupation() == val) {
						l.add(character);
					}
				}
			} else if (type == ADDRESS) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getAddress() == val) {
						l.add(character);
					}
				}
			} else if (type == CITY) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getCity() == val) {
						l.add(character);
					}
				}
			} else if (type == REGION) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getRegion() == val) {
						l.add(character);
					}
				}
			} else if (type == POSTAL) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getPostal() == val) {
						l.add(character);
					}
				}
			} else if (type == COUNTRY) {
				List<Character> l = matchesAny.get(type);
				if (l == null) {
					l = new LinkedList<Character>();
					matchesAny.put(type, l);
				}
				for (Character character : characters) {
					if (character.getCountry() == val) {
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

		List<Character> matchesAll = new LinkedList<Character>();

		// Now create a master list of people that match ALL of the given
		// attributes

		// We need access to specific keys, so copy them into a list
		ArrayList<String> types = new ArrayList<String>();
		types.addAll(attributes.keySet());

		// Compare lists of characters with other lists of characters
		// looking for duplicates for matches. If there was only one attribute
		// searched for, then return the full list
		if (types.size() == 1) {
			matchesAll = matchesAny.get(types.get(0));
		} else {
			for (int i = 0; i < (types.size() - 1); i++) {
				List<Character> temp1 = matchesAny.get(types.get(i));
				List<Character> temp2 = matchesAny.get(types.get(i + 1));

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

		// If chars is empty, create some terms with the specified data for the
		// game play database
		if (matchesAll.isEmpty()) {
			for (int i = 0; i < NEW_CHARACTERS; i++) {
				Character temp = super.getCharacter();
				// change the random data to the specific data
				// That way it will LOOK like data was found
				for (String type : attributes.keySet()) {
					String val = attributes.get(type);
					if (type == FIRSTNAME) {
						temp.setFirstName(val);
					} else if (type == LASTNAME) {
						temp.setLastName(val);
					} else if (type == DOB) {
						temp.setDob(val);
					} else if (type == DRIVERSID) {
						temp.setDriversID(val);
					} else if (type == PASSPORTID) {
						temp.setPassportID(val);
					} else if (type == OCCUPATION) {
						temp.setOccupation(val);
					} else if (type == ADDRESS) {
						temp.setAddress(val);
					} else if (type == CITY) {
						temp.setCity(val);
					} else if (type == REGION) {
						temp.setRegion(val);
					} else if (type == POSTAL) {
						temp.setPostal(val);
					} else if (type == COUNTRY) {
						temp.setCountry(val);
					} else {
						// ERROR, attribute key that was not recognized. This is
						// a programmers error, and thus, end the program.
						throw new RuntimeException(
								"Invalid Character Attribute " + type);
					}
				}
				// add temp to the master list, and the matches list.
				matchesAll.add(temp);
				characters.add(temp);
			}
		}
		return matchesAll;
	}

	// TODO: REMOVE ME!!
	public static void main(String[] args) {

		CharacterRepository repo = new CharacterRepository();
		Character c = repo.getCharacter();
		System.out.println("INITAL Char:");
		System.out.println(c.toString());

		Map<String, String> map = new HashMap<String, String>();
		map.put(FIRSTNAME, c.getFirstName());
		List<Character> l = repo.getCharacters(map);

		System.out.println("\nChars found in search query:\n");
		System.err.println(l.size());

		if (l.contains(c)) {
			for (Character chara : l) {
				System.out.println(chara.toString());
			}
		}

	}
}