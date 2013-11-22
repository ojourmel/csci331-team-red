package csci331.team.red.shared;

/**
 * The result of a database query from the player.<br>
 * FIXME: <b>Stub</b> <br>
 * FIXME: Possible naming conflicts with actual database queries...
 * 
 * @author ojourmel
 */
public class Result {

	/**
	 * An INVALID COMMAND result
	 */
	public static final Result INVALID_COMMAND = new Result(
			"Faild to parse command!");

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Result() {
	}

	/**
	 * Create a new result.
	 */
	public Result(String resultText) {
		this.resultText = resultText;
	}

	private String resultText;

	public String getResultText() {
		return resultText;
	}
}
