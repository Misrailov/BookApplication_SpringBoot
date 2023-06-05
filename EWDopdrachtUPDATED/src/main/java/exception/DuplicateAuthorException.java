package exception;

public class DuplicateAuthorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DuplicateAuthorException(String string) {
		super(String.format("Duplicate Author %s", string));
	}

}
