package validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookValidation{
	private final BookValidationProperties validationProperties;
	
	@Autowired
	public BookValidation(BookValidationProperties validationProperties) {
		this.validationProperties =validationProperties;
	}
	
	public String getNameNotBlankMessage() {
		return validationProperties.getNameNotBlankMessage();
	}
	public String getPriceNotNullkMessage() {
		return validationProperties.getPriceNotNullMessage();
	}
	public String getPriceFormatIncorrectMessage() {
		return validationProperties.getPriceFormatIncorrectMessage();
	}
	public String getISBNPatternIncorrectMessage() {
		return validationProperties.getISBNPatternIncorrectMessage();
	}
	public String getISBNChecksumIncorrectMessage() {
		return validationProperties.getISBNChecksumIncorrectMessage();
	}
	
}