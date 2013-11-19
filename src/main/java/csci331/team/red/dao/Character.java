package csci331.team.red.dao;

import csci331.team.red.shared.Gender;
import csci331.team.red.shared.PersonPicture;

/**
 * Contains specific game character information, generated from the
 * CharacterDAO.<br>
 * <br>
 * 
 * CSCI331-TAG MW ENCAPSULATION<br>
 * <br>
 * By creating getters and setters for all attributes of the Character class,
 * implementation is hidden from the outside world. This also prevents unwanted
 * access from the outside world <br>
 * <br>
 * Game Character Information:
 * <ul>
 * <li>Date of Birth</li>
 * <li>Driver's License Number</li>
 * <li>Last Name</li>
 * <li>First Name</li>
 * <li>Passport ID</li>
 * <li>Address</li>
 * <li>City</li>
 * <li>Country</li>
 * <li>Postal Code</li>
 * <li>Occupation</li>
 * <li>Gender</li>
 * <li>PersonPicture</li>
 * </ul>
 * 
 * @author melany
 */
public class Character {

	// Character Attribute Variables
	private String firstName;
	private String lastName;
	private String dob;
	private String driversID;
	private String passportID;
	private String occupation;
	private String address;
	private String city;
	private String region;
	private String postal;
	private String country;
	private Gender gender;
	private PersonPicture personPicture;

	// Getters/Setters for Attribute Variables
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDriversID() {
		return driversID;
	}

	public void setDriversID(String driversID) {
		this.driversID = driversID;
	}

	public String getPassportID() {
		return passportID;
	}

	public void setPassportID(String passportID) {
		this.passportID = passportID;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public PersonPicture getPersonPicture() {
		return personPicture;
	}

	public void setPersonPicture(PersonPicture personPicture) {
		this.personPicture = personPicture;
	}

	/**
	 * 
	 * Character Constructor<br>
	 * Initializes character attributes to given values
	 * 
	 * @param dob
	 * @param driversID
	 * @param firstName
	 * @param lastName
	 * @param passportID
	 * @param address
	 * @param city
	 * @param region
	 * @param postal
	 * @param country
	 * @param occupation
	 * @param gender
	 * @param personPicture
	 * 
	 * @author melany
	 */
	Character(String dob, String driversID, String firstName, String lastName,
			String passportID, String address, String city, String region,
			String postal, String country, String occupation, Gender gender,
			PersonPicture personPicture) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.driversID = driversID;
		this.passportID = passportID;
		this.occupation = occupation;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postal = postal;
		this.country = country;
		this.gender = gender;
		this.personPicture = personPicture;

	}

	/**
	 * CSCI331-TAG MW OVERRIDE<br>
	 * <br>
	 * 
	 * Another override, this one overrides one of the built-in java methods
	 * rather than two methods I've wrote for different classes.<br>
	 * 
	 * I overrode this one because the built-in equals doesn't compare a
	 * Character object. In addition to overriding the equals method, I also
	 * overrode the hashCode in case there is every a hashMap of Characters
	 * Instantiated, which is shown below.
	 * 
	 * @author melany
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		// if the two objects are equal in reference, they are equal
		if (this == obj) {
			return true;
		} else if (obj instanceof Character) {
			Character c = (Character) obj;
			if ((((c.firstName == null) && (this.firstName == null)) || (c
					.firstName.equals(this.firstName)))
					&& (((c.lastName == null) && (this.lastName == null)) || (c
							.lastName.equals(this.lastName)))
					&& (((c.dob == null) && (this.dob == null)) || (c
							.dob.equals(this.dob)))
					&& (((c.driversID == null) && (this.driversID == null)) || (c
							.driversID.equals(this.driversID)))
					&& (((c.passportID == null) && (this.passportID == null)) || (c
							.passportID.equals(this.passportID)))
					&& (((c.occupation == null) && (this.occupation == null)) || (c
							.occupation.equals(this.occupation)))
					&& (((c.address == null) && (this.address == null)) || (c
							.address.equals(this.address)))
					&& (((c.city == null) && (this.city == null)) || (c
							.city.equals(this.city)))
					&& (((c.region == null) && (this.region == null)) || (c
							.region.equals(this.region)))
					&& (((c.postal == null) && (this.postal == null)) || (c
							.postal.equals(this.postal)))
					&& (((c.country == null) && (this.country == null)) || (c
							.country.equals(this.country)))
							
					&& (((c.gender == null) && (this.gender == null)) || (c
							.gender.equals(this.gender)))
					&& (((c.personPicture == null) && (this.personPicture == null)) || (c
							.personPicture.equals(this.personPicture)))
							
					) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * hashCode override, in case there is every a hashMap of Characters
	 * created.
	 * 
	 * @author melany
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = hashCode * 37 + firstName.hashCode();
		hashCode = hashCode * 37 + lastName.hashCode();
		hashCode = hashCode * 37 + dob.hashCode();
		hashCode = hashCode * 37 + driversID.hashCode();
		hashCode = hashCode * 37 + passportID.hashCode();
		hashCode = hashCode * 37 + occupation.hashCode();
		hashCode = hashCode * 37 + address.hashCode();
		hashCode = hashCode * 37 + city.hashCode();
		hashCode = hashCode * 37 + region.hashCode();
		hashCode = hashCode * 37 + postal.hashCode();
		hashCode = hashCode * 37 + country.hashCode();
		hashCode = hashCode * 37 + gender.hashCode();
		hashCode = hashCode * 37 + personPicture.hashCode();

		return hashCode;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("Name: ");
		sb.append(firstName);
		sb.append(" ");
		sb.append(lastName);
		sb.append("\n");
		
		sb.append("Gender: ");
		sb.append(gender);
		sb.append("\n");

		sb.append("Born: ");
		sb.append(dob);
		sb.append("\n");
		sb.append("Occupation: ");
		sb.append(occupation);
		sb.append("\n");
		sb.append("\n");

		sb.append("Address:\n");
		sb.append(address);
		sb.append("\n");
		sb.append(city);
		sb.append(", ");
		sb.append(region);
		sb.append("\n");
		sb.append(postal);
		sb.append("\n");
		sb.append(country);
		sb.append("\n");
		sb.append("\n");

		sb.append("Drivers Licence: ");
		sb.append(driversID);
		sb.append("\n");
		sb.append("Passport ID: ");
		sb.append(passportID);
		return sb.toString();
	}

}