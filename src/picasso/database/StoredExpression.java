package picasso.database;

/*
 * Represents an expression stored in the database.
 * 
 * @author Gabriel Hogan
 */
public class StoredExpression {
	private long expId;
	private String expStr;
	private String expName;
	private String evaluatedAt;

	public StoredExpression(long expId, String expStr, String expName, String evaluatedAt) {
		this.expId = expId;
		this.expStr = expStr;
		this.expName = expName;
		this.evaluatedAt = evaluatedAt;
	}

	public long getExpId() {
		return expId;
	}

	public String getExpStr() {
		return expStr;
	}

	public String getExpName() {
		return expName;
	}

	public String getEvaluatedAt() {
		return evaluatedAt;
	}

	@Override
	public String toString() {
		return "Expression{" + "expId=" + expId + ", expStr='" + expStr + '\'' + ", expName='" + expName + '\''
				+ ", evaluatedAt='" + evaluatedAt + '\'' + '}';
	}
}