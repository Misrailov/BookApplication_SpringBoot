package validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IsbnPatternValidator.class)
public @interface ValidISBNNumberPattern {

	String message() default "Invalid ISBN pattern";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}