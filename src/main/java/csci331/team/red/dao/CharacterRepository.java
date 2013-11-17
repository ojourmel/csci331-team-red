package csci331.team.red.dao;

//import static csci331.team.red.dao.CharacterDAO.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * CSCI331-TAG MW INTERFACE<br>
 * 
 * The CharacterRepository Class provides an interface for game play. It is the
 * only class that other members use to interact with the database and the
 * current game play character database<br><br>
 * 
 * CSCI331-TAG MW SUBCLASS<br>
 * 
 * The CharacterRepository Class extends from the CharacterDAO class as it uses
 * the static global variables instantiated in CharacterDAO<br><br>
 * 
 * @author melany
 * 
 */
public class CharacterRepository extends CharacterDAO {

	ArrayList<Character> characters;
	private static final int INITIAL_CHARACTERS=50;
	private static final int NEW_CHARACTERS=10;

	/**
	 * The construction creates an initial game-play character database
	 * of 50 random characters
	 * @author melany
	 */
	CharacterRepository() {
		characters = new ArrayList<Character>();
		CharacterDAO characterDAO = new CharacterDAO();

		// Create the first 50 characters for game play
		for (int i = 0; i < INITIAL_CHARACTERS; i++) {
			characters.add(characterDAO.getCharacter());
		}
	}

	/**
	 * CSCI331-TAG MW OVERRIDING <br>
	 * <br>
	 * 
	 * Returns a random character in the game play character database
	 * This is different from the CharacterDAO.getCharacter, which creates
	 * a random character
	 * 
	 * @return Character
	 * @author melany
	 */
	public Character getCharacter() {
		int range = (int) (Math.random() * (characters.size()));
		return characters.get(range);
	}

	/**
	 * Iterates through the game-play characters looking for 
	 * characters that match the <key,value> pair supplied in the 
	 * parameter.  All matches are added to the return list of characters.<br>
	 * <br>
	 * If there are no characters in the game-play database that have
	 * the required parameters, new characters will be created that have
	 * the parameter, added to the character list AND to the game play database
	 * and returned.
	 * @param map
	 * @return List of Characters, returns null on error
	 */
	public List<Character> getCharacters(Map<String, String> map) {
		List<Character> chars = new LinkedList<Character>();

		//Go through and see if the key is a database attribute
		//If found, we add that character to the chars list
		for (String s : map.keySet()) {
			String val = map.get(s);

			if (s == FIRSTNAME) {
				for(Character character : characters){
					if(character.getFirstName() == val){
						chars.add(character);
					}
				}

			} else if (s == LASTNAME) {
				for(Character character : characters){
					if(character.getLastName() == val){
						chars.add(character);
					}
				}
			} else if (s == DOB) {
				for(Character character : characters){
					if(character.getDob() == val){
						chars.add(character);
					}
				}
			} else if (s == DRIVERSID) {
				for(Character character : characters){
					if(character.getDriversID() == val){
						chars.add(character);
					}
				}
			} else if (s == PASSPORTID) {
				for(Character character : characters){
					if(character.getPassportID() == val){
						chars.add(character);
					}
				}

			} else if (s == OCCUPATION) {
				for(Character character : characters){
					if(character.getOccupation() == val){
						chars.add(character);
					}
				}
			} else if (s == ADDRESS) {
				for(Character character : characters){
					if(character.getAddress() == val){
						chars.add(character);
					}
				}
			} else if (s == CITY) {
				for(Character character : characters){
					if(character.getCity() == val){
						chars.add(character);
					}
				}
			} else if (s == REGION) {
				for(Character character : characters){
					if(character.getRegion() == val){
						chars.add(character);
					}
				}
			} else if (s == POSTAL) {
				for(Character character : characters){
					if(character.getPostal() == val){
						chars.add(character);
					}
				}
			} else if (s == COUNTRY) {
				for(Character character : characters){
					if(character.getCountry() == val){
						chars.add(character);
					}
				}
			} else {
				// ERROR return null
				return null;
			}
		}

		// If chars is empty, create some terms with the specified data for the
		// game play database
		if (chars.isEmpty()) {
			CharacterDAO characterDAO = new CharacterDAO();
			for (int i = 0; i < NEW_CHARACTERS; i++) {
				Character temp = characterDAO.getCharacter();
				// change the random data to the specific data
				for (String s : map.keySet()) {
					String val = map.get(s);
					if (s == FIRSTNAME) {
						temp.setFirstName(val);
					} else if (s == LASTNAME) {
						temp.setLastName(val);
					} else if (s == DOB) {
						temp.setDob(val);
					} else if (s == DRIVERSID) {
						temp.setDriversID(val);
					} else if (s == PASSPORTID) {
						temp.setPassportID(val);
					} else if (s == OCCUPATION) {
						temp.setOccupation(val);
					} else if (s == ADDRESS) {
						temp.setAddress(val);
					} else if (s == CITY) {
						temp.setCity(val);
					} else if (s == REGION) {
						temp.setRegion(val);
					} else if (s == POSTAL) {
						temp.setPostal(val);
					} else if (s == COUNTRY) {
						temp.setCountry(val);
					} else {
						// ERROR return null
						return null;
					}
				}
				// add temp to chars list and to characters arrayList
				chars.add(temp);
				characters.add(temp);
			}
		}

		return chars;
	}

	/**
	 * CSCI331-TAG MW OVERLOADING (NOT A REQUIRED TAG BUT I USED IT ANYWAYS)
	 * 
	 * Overloaded the getCharacters method, so that searches on more than one
	 * attribute can be used.  <br>
	 * It takes in a list of mappings, and iterates through each map
	 * looking for attributes in the game-play database that match.  <br>
	 * Lists of characters that are returned are then compared to the another
	 * list of characters.  Any duplicates are stored in the final 
	 * list of characters that are returned.<br>
	 * If there are no matches, than a set of new characters are created, and 
	 * there data is made to match the supplied attributes.  These new characters
	 * are added both the the list of characters that are returned, and to the
	 * game-play database of characters.
	 * 
	 * @param maps
	 * @return List(Character)
	 * 
	 * @author melany
	 */
	public List<Character> getCharacters(List<Map<String, String>> maps) {
		List<Character> chars = new LinkedList<Character>();

		//Compare lists of characters with other lists of characters
		//looking for duplicates for matches
		for (int i = 0; i < (maps.size()-1); i++) {
			List<Character> temp1 = getCharacters((maps.get(i++)));
			List<Character> temp2 = getCharacters((maps.get(i)));

			for (int j = 0; j < (temp1.size()); j++) {
				Character t1 = temp1.get(j);
				for (int k = 0; k < (temp2.size()); k++) {
					Character t2 = temp2.get(k);
					if (t1.equals(t2)) {
						chars.add(t1);
					}
				}
			}
		}

		// Now to make sure there is something in chars, if not, have to add to
		// characters and try again
		if (chars.isEmpty()) {
			CharacterDAO characterDAO = new CharacterDAO();
			for (int i = 0; i < NEW_CHARACTERS; i++) {
				Character temp = characterDAO.getCharacter();
				// change the random data to the specific data

				for (int j = 0; j < maps.size(); j++) {
					Map<String, String> map = maps.get(j);
					for (String s : map.keySet()) {
						String val = map.get(s);
						if (s == FIRSTNAME) {
							temp.setFirstName(val);
						} else if (s == LASTNAME) {
							temp.setLastName(val);
						} else if (s == DOB) {
							temp.setDob(val);
						} else if (s == DRIVERSID) {
							temp.setDriversID(val);
						} else if (s == PASSPORTID) {
							temp.setPassportID(val);
						} else if (s == OCCUPATION) {
							temp.setOccupation(val);
						} else if (s == ADDRESS) {
							temp.setAddress(val);
						} else if (s == CITY) {
							temp.setCity(val);
						} else if (s == REGION) {
							temp.setRegion(val);
						} else if (s == POSTAL) {
							temp.setPostal(val);
						} else if (s == COUNTRY) {
							temp.setCountry(val);
						} else {
							// ERROR return null
							return null;
						}
					}

				}
				// add temp to chars list and to characters arrayList
				chars.add(temp);
				characters.add(temp);
			}
		}

		return chars;
	}

}