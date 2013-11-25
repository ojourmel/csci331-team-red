package csci331.team.red.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for {@link Gender}
 * 
 * @author marius
 */
public class GenderTest {
	private static Gender gender = null;

	@Before
	public void setUp() throws Exception {
		gender = null;
	}

	@Test
	public void testEnum() {
		gender = Gender.FEMALE;
		assertEquals("Enum failed to set gender", gender, Gender.FEMALE);

		gender = Gender.MALE;
		assertEquals("Enum failed to set gender", gender, Gender.MALE);
	}

	@Test
	public void testfromChar() {
		gender = Gender.fromChar('f');
		assertEquals("fromChar failed to set gender", gender, Gender.FEMALE);

		gender = Gender.fromChar('m');
		assertEquals("fromChar failed to set gender", gender, Gender.MALE);

		gender = Gender.fromChar('F');
		assertEquals("fromChar failed to set gender", gender, Gender.FEMALE);

		gender = Gender.fromChar('M');
		assertEquals("fromChar failed to set gender", gender, Gender.MALE);

		gender = Gender.fromChar('b');
		assertNull("Invalid fromChar did not return null", gender);
	}
}
