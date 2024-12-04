package picasso.parser.tokens;

/**
 * Represents an string identifier (a string name)
 */
public class StringToken extends Token{

	private final String myString;
	
	public StringToken(String value) {
		super("String Token");
		myString = value;
	}
	
	public boolean equals(Object o) {
		if( o == this ) {
			return true;
		}
		if (!(o instanceof StringToken)) {
			return false;
		}
		StringToken rhs = (StringToken) o;
		return myString.equals(rhs.myString);
	}

	/**
	 * Returns the string's name
	 * @return the string's name
	 */
	public String getName() {
		return myString;
	}

	public String toString() {
		return super.toString() + ": " + myString;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

	
}
