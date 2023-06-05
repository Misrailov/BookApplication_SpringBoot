package exception;

public class LocatieNotFoundException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LocatieNotFoundException(Long id) {
		super(String.format("Location with  %s id not found", id));
	}
}
