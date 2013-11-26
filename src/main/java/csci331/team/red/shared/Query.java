package csci331.team.red.shared;

public class Query {
	public String queryText;

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Query() {
	}

	public Query(String s) {

		queryText = s;

	}

}
