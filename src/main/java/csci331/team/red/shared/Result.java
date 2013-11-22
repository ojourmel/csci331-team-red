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
	 * An INVALID result
	 */
	public static final Result INVALID = new Result(-1);

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Result() {
	}

	/**
	 * Create a new result.
	 * 
	 * @param id
	 *            of the new {@link Result}
	 */
	public Result(int id) {
		// TODO Auto-generated constructor stub
	}

	public String resultText;
}
