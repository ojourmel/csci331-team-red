package csci331.team.red.server;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import csci331.team.red.dao.CharacterDAO;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Incident;

/**
 * 
 * Not actually JUnit unit tests, simply manual verification tests to view some
 * dialogue generation.
 * 
 * @author ojourmel
 */
public class DialogueHandlerTest {

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
	public void momTest() {

		System.out.println("************GenericAlerts************");
		System.out.println("\n");

		CharacterDAO dao = new CharacterDAO();
		DialogueHandler handle = new DialogueHandler(new Random());
		for (int i = 0; i < 50; i++) {

			for (Dialogue d : handle.momsCuriosity(new Incident(dao
					.getCharacter()))) {
				System.out.println(d.getDialogue() + " : " + d.getSpeaker());
				System.out.println();
			}
			System.out.println();
		}
	}
}
