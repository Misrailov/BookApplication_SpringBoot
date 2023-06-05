package exception;

public class BookNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BookNotFoundException(Long id) {
		super(String.format("Book with %s not Found", id));
	}
	public BookNotFoundException(String isbn) {
		super(String.format("Book with %s ISBN not Found", isbn));
	}

}
