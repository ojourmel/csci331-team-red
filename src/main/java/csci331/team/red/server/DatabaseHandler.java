package csci331.team.red.server;

import static csci331.team.red.dao.CharacterDAO.ADDRESS;
import static csci331.team.red.dao.CharacterDAO.CITY;
import static csci331.team.red.dao.CharacterDAO.COUNTRY;
import static csci331.team.red.dao.CharacterDAO.DOB;
import static csci331.team.red.dao.CharacterDAO.DRIVERSID;
import static csci331.team.red.dao.CharacterDAO.FIRSTNAME;
import static csci331.team.red.dao.CharacterDAO.LASTNAME;
import static csci331.team.red.dao.CharacterDAO.OCCUPATION;
import static csci331.team.red.dao.CharacterDAO.PASSPORTID;
import static csci331.team.red.dao.CharacterDAO.REGION;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import csci331.team.red.dao.CharacterRepository;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Result;

public class DatabaseHandler {

	private String HELP = "help";
	private String ACTION = "search";
	private String DB = "prism";

	private final CharacterRepository repo;

	public DatabaseHandler(CharacterRepository repo) {
		this.repo = repo;
	}

	public Result startUp() {

		StringBuilder start = new StringBuilder();

		start.append("Thankyou for using DataSearchPro");
		start.append("\n");

		start.append("Please refer to \"help\" should you become confused.");
		start.append("\n");

		start.append("If confusion persists, after the use of the help screen, contact your local DataSearchPro representative, using your confidential customer ID.");
		start.append("\n");

		return new Result(start.toString());
	}

	public List<Result> execute(String command, Incident incident) {

		List<Result> results = new LinkedList<Result>();

		if (command.equals(HELP)) {
			results.add(help());
			return results;
		}

		// split on whitespace
		String[] tokenizedString = command.split("\\s", 3);

		if (tokenizedString.length < 3) {
			results.add(Result.INVALID_COMMAND);
			return results;
		}

		if (!tokenizedString[0].equals(ACTION)) {
			results.add(new Result("No command " + tokenizedString[0]));
			return results;
		}

		else if (!(tokenizedString[1].equals(DB))) {
			results.add(new Result(
					"Failed to acquire hook to search database!\nAre you accessing the correct one?"));
			return results;
		}

		// now, each string will represent some search parameter...

		Set<Character> characters = new HashSet<Character>();

		for (int i = 2; i < tokenizedString.length; i++) {
			String param = tokenizedString[i];

			HashMap<String, String> search = new HashMap<String, String>();

			search.put(FIRSTNAME, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(DRIVERSID, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(DOB, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(PASSPORTID, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(ADDRESS, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(CITY, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(REGION, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(COUNTRY, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();

			search.put(OCCUPATION, param);
			characters.addAll(repo.getCharacters(search));
			search.clear();
		}

		if (characters.isEmpty()) {
			results.add(new Result("No matches found"));
		}

		for (Character character : characters) {
			results.add(new Result(character.toString()));
		}

		return results;
	}

	private Result help() {

		StringBuilder help = new StringBuilder();

		help.append("Usage: ");
		help.append("\n");
		help.append("<search> <prism> [param] [param] [or] [para]");
		help.append("\n");

		help.append("Example: \"search prism foo\" will return all entries matching foo");
		help.append("\n");

		return new Result(help.toString());
	}
}
