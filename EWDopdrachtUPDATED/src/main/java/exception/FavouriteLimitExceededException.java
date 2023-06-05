package exception;

public class FavouriteLimitExceededException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FavouriteLimitExceededException(String name) {
		super(String.format("Limit of favourited books exceeded", name));
	}

}
