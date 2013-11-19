package csci331.team.red.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

import csci331.team.red.shared.Gender;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.PersonPicture;

/**
 * Tester class for {@link Character}
 * 
 * @author melany
 */
public class CharacterTest
{

	private static Character x;
	private static Character y;
	private static Character z;
	private static Character other;

	@BeforeClass
	public static void setUp()
	{

		// We need some Characters to work with, and they are kind of annoying
		// to create, so lets make some now...
		x = new Character("dob", "driverID", "fName", "lName", "passId",
				"address", "city", "region", "postal", "contry", "occupation",
				Gender.FEMALE, PersonPicture.FEMALE1);
		y = new Character("dob", "driverID", "fName", "lName", "passId",
				"address", "city", "region", "postal", "contry", "occupation",
				Gender.FEMALE, PersonPicture.FEMALE1);
		z = new Character("dob", "driverID", "fName", "lName", "passId",
				"address", "city", "region", "postal", "contry", "occupation",
				Gender.FEMALE, PersonPicture.FEMALE1);
		other = new Character("otherDob", "ohterDriverID", "fName", "lName",
				"passId", "address", "city", "region", "postal", "contry",
				"occupation", Gender.MALE, PersonPicture.MALE1);
	}

	/**
	 * @see {@link Object#hashCode()}
	 */
	@Test
	public void testHashCode()
	{

		assertEquals("Hashcode is not consistant!", x.hashCode(), x.hashCode());

		if (x.equals(y))
		{
			assertEquals("Hashcode is not equivalent!", x.hashCode(),
					y.hashCode());
		}
	}

	/**
	 * @see {@link Object#equals(Object)}
	 */
	@Test
	public void testEquals()
	{

		assertEquals("It is NOT reflexive!", x, x);
		if (x.equals(y))
		{
			assertEquals("It is NOT symmetric!", y, x);
		}

		if (x.equals(y) && (y.equals(z)))
		{
			assertEquals("It is NOT transitive!", x, z);
		}

		assertEquals("It is NOT consistent!", x.equals(y), x.equals(y));

		assertFalse("It is NOT null safe!", x.equals(null));

		assertFalse("Always returns equals!", x.equals(other));
	}
}
