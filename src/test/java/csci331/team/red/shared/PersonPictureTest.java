/**
 * 
 */
package csci331.team.red.shared;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tester class for {@link PersonPicture}
 * 
 * @author marius
 */
public class PersonPictureTest {
	private static PersonPicture persPic = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		persPic = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEnum() {
		persPic = PersonPicture.INTROFEMALE0;
		assertEquals("Enum failed to set persPic", persPic,
				PersonPicture.INTROFEMALE0);

		persPic = PersonPicture.MALE1;
		assertEquals("Enum failed to set persPic", persPic, PersonPicture.MALE1);

		persPic = PersonPicture.THUG1;
		assertEquals("Enum failed to set persPic", persPic, PersonPicture.THUG1);
	}

	/**
	 * Test method for {@link PersonPicture#getRandom(Gender)}.
	 */
	@Test
	public void testGetRandom() {
		persPic = PersonPicture.getRandom(Gender.FEMALE);
		assertEquals("getRandom gender incorrectly set", Gender.FEMALE,
				persPic.g);
		persPic = PersonPicture.getRandom(Gender.MALE);
		assertEquals("getRandom gender incorrectly set", Gender.MALE, persPic.g);

		persPic = PersonPicture.getRandom(Gender.fromChar('b'));
		// TODO: Complete me
	}

}
