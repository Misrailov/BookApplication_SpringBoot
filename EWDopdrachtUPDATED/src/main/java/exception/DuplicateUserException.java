package exception;

public class DuplicateUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicateUserException(String username) {
		super(String.format("Duplicate User %S", username));
	}

}
