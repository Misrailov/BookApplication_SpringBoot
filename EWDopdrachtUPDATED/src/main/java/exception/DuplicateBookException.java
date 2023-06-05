package exception;

public class DuplicateBookException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DuplicateBookException(Long id) {
		super(String.format("Duplicate Book %s", id));
	}
	public DuplicateBookException(String ISBN) {
		super(String.format("Duplicate Book %s ISBN", ISBN));
	}
}
