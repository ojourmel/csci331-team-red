package csci331.team.red.dao;


/**
 * Contains specific game character information, generated from the
 * CharacterDAO.<br>
 * <br>
 * 
 * CSCI331-TAG MW ENCAPSULATION<br>
 * <br>
 * By creating getters and setters for all attributes of the Character class,
 * implementation is hidden from the outside world. This also prevents unwanted
 * access from the outside world
 * <br> <br>
 * Game Character Information:
 * <ul>
 * <li>Date of Birth</li>
 * <li>Driver's Licience Number</li>
 * <li>Last Name</li>
 * <li>First Name</li>
 * <li>Passport ID</li>
 * <li>Address</li>
 * <li>City</li>
 * <li>Country</li>
 * <li>Postal Code</li>
 * <li>Occupation</li>
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
	 * 
	 * @author melany
	 */
	Character(String dob, String driversID, String firstName, String lastName,
			String passportID, String address, String city, String region,
			String postal, String country, String occupation) {

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

	}

	/**
	 * CSCI331-TAG MW OVERRIDE<br><br>
	 * 
	 * Another override, this one overrides one of the built-in java methods
	 * rather than two methods I've wrote for different classes.<br>
	 * 
	 * I overrode this one because the built-in equals doesn't compare a 
	 * Character object.  In addition to overriding the equals method, I also 
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
			if ((((c.getFirstName() == null) && (this.getFirstName() == null)) || (c
					.getFirstName().equals(this.getFirstName())))
					&& (((c.getLastName() == null) && (this.getLastName() == null)) || (c
							.getLastName().equals(this.getLastName())))
					&& (((c.getDob() == null) && (this.getDob() == null)) || (c
							.getDob().equals(this.getDob())))
					&& (((c.getDriversID() == null) && (this.getDriversID() == null)) || (c
							.getDriversID().equals(this.getDriversID())))
					&& (((c.getPassportID() == null) && (this.getPassportID() == null)) || (c
							.getPassportID().equals(this.getPassportID())))
					&& (((c.getOccupation() == null) && (this.getOccupation() == null)) || (c
							.getOccupation().equals(this.getOccupation())))
					&& (((c.getAddress() == null) && (this.getAddress() == null)) || (c
							.getAddress().equals(this.getAddress())))
					&& (((c.getCity() == null) && (this.getCity() == null)) || (c
							.getCity().equals(this.getCity())))
					&& (((c.getRegion() == null) && (this.getRegion() == null)) || (c
							.getRegion().equals(this.getRegion())))
					&& (((c.getPostal() == null) && (this.getPostal() == null)) || (c
							.getPostal().equals(this.getPostal())))
					&& (((c.getCountry() == null) && (this.getCountry() == null)) || (c
							.getCountry().equals(this.getCountry())))
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

		hashCode = hashCode * 37 + this.firstName.hashCode();
		hashCode = hashCode * 37 + this.lastName.hashCode();
		hashCode = hashCode * 37 + this.dob.hashCode();
		hashCode = hashCode * 37 + this.driversID.hashCode();
		hashCode = hashCode * 37 + this.passportID.hashCode();
		hashCode = hashCode * 37 + this.occupation.hashCode();
		hashCode = hashCode * 37 + this.address.hashCode();
		hashCode = hashCode * 37 + this.city.hashCode();
		hashCode = hashCode * 37 + this.region.hashCode();
		hashCode = hashCode * 37 + this.postal.hashCode();
		hashCode = hashCode * 37 + this.country.hashCode();

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