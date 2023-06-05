package validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnPatternValidator implements ConstraintValidator<ValidISBNNumberPattern, String> {

    private static final String ISBN_PATTERN = "^(\\d{1,5}-)?\\d{1,7}-\\d{1,6}-[\\d|X]$";

    @Override
    public void initialize(ValidISBNNumberPattern constraintAnnotation) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
    	 isbn = isbn.replace("-", "");
    	 isbn = isbn.replace(" ", "");

         // Check if the ISBN is 13 digits long
         if (isbn.length() != 13) {
             return false;
         }

         // Check if the characters are all numeric
         if (!isbn.matches("[0-9]+")) {
             return false;
         }
		return true;
    }
}