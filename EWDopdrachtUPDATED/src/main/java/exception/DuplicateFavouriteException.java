package exception;

public class DuplicateFavouriteException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateFavouriteException(Long id) {
		super(String.format("Favourite with id %s already exists", id));
	}
}
