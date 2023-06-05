package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNNumberConstraintValidator implements ConstraintValidator<ValidISBNNumberChecksum, String> {

    @Override
    public void initialize(ValidISBNNumberChecksum constraintAnnotation) {}
	@Override
	public boolean isValid(String isbn, ConstraintValidatorContext context) {
        isbn = isbn.replace("-", "");

        // Check if the ISBN is 13 digits long
        if (isbn.length() != 13) {
            System.out.println("Het formaat van het ISBN-nummer is onjuist. Het moet 13-cijferig zijn.");
            return false;
        }

        // Check if the characters are all numeric
        if (!isbn.matches("[0-9]+")) {
            System.out.println("Het formaat van het ISBN-nummer is onjuist. Het moet alleen cijfers bevatten.");
            return false;
        }

        // Calculate the checksum digit
        int checksum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            checksum += (i % 2 == 0) ? digit : digit * 3;
        }
        checksum = 10 - (checksum % 10);
        if (checksum == 10) {
            checksum = 0;
        }

        // Check if the calculated checksum matches the last digit of the ISBN
        int lastDigit = Character.getNumericValue(isbn.charAt(12));
        if (checksum != lastDigit) {
            System.out.println("Er staat een fout in het ISBN-nummer (via het controlecijfer).");
            return false;
        }

        // The ISBN is valid
        return true;
		
	}
	

}
