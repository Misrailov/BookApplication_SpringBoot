package exception;

public class FavouriteNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FavouriteNotFoundException(Long id) {
		super(String.format("Favourite with id %s not found", id));
	}
}
