package exception;

public class AuthorNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AuthorNotFoundException(Long id) {
		super(String.format("Author with id %s not found",id));
	}

}
