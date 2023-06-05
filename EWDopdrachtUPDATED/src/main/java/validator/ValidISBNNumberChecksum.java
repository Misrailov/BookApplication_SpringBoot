package validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ISBNNumberConstraintValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ValidISBNNumberChecksum {
	
    String message() default "checksum_fail";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
