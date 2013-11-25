package csci331.team.red.server;

import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Result;

public class DatabaseHandler {

	public Result execute(String command, Incident incident) {
		String[] tokenizedString = command.split("\\s", 3);

		if (tokenizedString.length < 3) {
			return Result.INVALID_COMMAND;
		}

		if (!tokenizedString[0].equalsIgnoreCase("search")) {
			return Result.INVALID_COMMAND;
		}

		else if (!(tokenizedString[1].equalsIgnoreCase("prism"))) {
			return new Result("Failed to acquire hook to search database!");
		}

		else if (!tokenizedString[2].equalsIgnoreCase("Mary Test")
				&& !tokenizedString[2].equalsIgnoreCase("123456789")) {
			return new Result("Error:  " + tokenizedString[2]
					+ " does not exist.");

		}

		else {
			if (tokenizedString[2].equalsIgnoreCase("Mary Test")) {
				return new Result("Mary Test --\n" + "Age: 22\n"
						+ "Status: Deceased");
			} else {
				return new Result("DL 123456789\n" + "Name: John Smythe\n"
						+ "Outstanding Arrest Warrants");
			}
		}
	}
}
