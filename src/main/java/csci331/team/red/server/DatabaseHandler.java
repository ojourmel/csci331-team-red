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

	private String NAME = "name";
	private String NUMBER = "number";
	private String JOB = "occupation";
	private String OTHER = "other";

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
		String[] tokenizedString = command.split(" ");

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
		// Do a quick check for "mary test", as that will produce some rather
		// specal output.
		if (tokenizedString.length == 5) {
			if (tokenizedString[2].equalsIgnoreCase(NAME)
					&& tokenizedString[3].equalsIgnoreCase("Mary")
					&& tokenizedString[4].equalsIgnoreCase("Test")) {

				results.add(new Result(
						"Mary Test --\n Age: 22\n Status: Deceased"));
				return results;
			}
		}

		Set<Character> characters = new HashSet<Character>();
		HashMap<String, String> search = new HashMap<String, String>();

		for (int i = 2; i < tokenizedString.length; i++) {
			String param = tokenizedString[i];

			if (param.equals(NAME)) {
				if (i + 2 < tokenizedString.length) {
					i++;
					search.put(FIRSTNAME, tokenizedString[i]);
					i++;
					search.put(LASTNAME, tokenizedString[i]);
				} else if (i + 1 < tokenizedString.length) {
					i++;
					search.put(FIRSTNAME, tokenizedString[i]);
				} else {
					results.clear();
					results.add(Result.INVALID_COMMAND);
					return results;
				}

				characters.addAll(repo.getCharacters(search));
				search.clear();
			}

			if (param.equals(NUMBER)) {
				if (i + 1 < tokenizedString.length) {
					i++;
					search.put(DRIVERSID, tokenizedString[i]);

					characters.addAll(repo.getCharacters(search));
					search.clear();

					search.put(PASSPORTID, tokenizedString[i]);

					characters.addAll(repo.getCharacters(search));
					search.clear();

					search.put(DOB, tokenizedString[i]);

					characters.addAll(repo.getCharacters(search));
					search.clear();

					search.put(ADDRESS, tokenizedString[i]);

					characters.addAll(repo.getCharacters(search));
					search.clear();
				}
			}

			if (param.equals(JOB)) {

				String occ = "";

				i++;
				while (i < tokenizedString.length) {

					if (tokenizedString[i].equals(NAME)
							|| (tokenizedString[i].equals(NUMBER))
							|| (tokenizedString[i].equals(OTHER))) {
						i--;
						break;
					} else {
						occ += tokenizedString[i];
						occ += " ";
					}
				}

				occ.trim();

				search.put(OCCUPATION, occ);
				characters.addAll(repo.getCharacters(search));
				search.clear();
			}

			if (param.equals(OTHER)) {

				if (i + 1 < tokenizedString.length) {
					i++;
					param = tokenizedString[i];

					search.put(FIRSTNAME, param);
					characters.addAll(repo.getCharacters(search));
					search.clear();

					search.put(LASTNAME, param);
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
			}
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
		help.append("<search> <prism> <keyword> [param] ...");
		help.append("\n");

		help.append("Keywords: name<val>[val];\n number<val>;\n occupation<val>...;\n other<val>");
		help.append("\n");

		return new Result(help.toString());
	}
}
