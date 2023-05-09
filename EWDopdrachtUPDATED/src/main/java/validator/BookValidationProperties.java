package validator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "validation")
public class BookValidationProperties {

	public String getPriceNotNullMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNameNotBlankMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPriceFormatIncorrectMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getISBNPatternIncorrectMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getISBNChecksumIncorrectMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
