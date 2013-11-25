package csci331.team.red.shared;

/**
 * The result of a database query from the player.<br>
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

	/**
	 * @param resultText
	 *            to set
	 */
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	/**
	 * @return resultText to get
	 */
	public String getResultText() {
		return resultText;
	}
}
