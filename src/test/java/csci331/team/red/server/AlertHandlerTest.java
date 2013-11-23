package csci331.team.red.server;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import csci331.team.red.dao.CharacterRepository;

;

/**
 * 
 * Not actually JUnit unit tests, simply manual verification tests to view alert
 * generation
 * 
 * 
 * 
 * @author ojourmel
 */
public class AlertHandlerTest {

	@BeforeClass
	public static void setUp() {
		System.out.println("************AlertHandlerTest************");
		System.out.println("\n");
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("\n");
		System.out.println("****************************************");
	}

	@Test
	public void MANUALtest() {

		AlertHandler handle = new AlertHandler(new Random(),
				new CharacterRepository());
		for (int i = 0; i < 50; i++) {

			System.out.println(handle.genericAlert().alertText);
			System.out.println();
			System.out.println();
		}
	}
}
