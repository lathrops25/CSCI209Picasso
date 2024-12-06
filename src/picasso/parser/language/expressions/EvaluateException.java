package picasso.parser.language.expressions;

	/**
	 * Describe an exception that occurred during parsing.
	 * 
	 * @author Sarah Lathrop
	 *
	 */
	@SuppressWarnings("serial")
	public class EvaluateException extends RuntimeException {

		public EvaluateException(String message) {
			super("EvaluateException: " + message);
		}

	}