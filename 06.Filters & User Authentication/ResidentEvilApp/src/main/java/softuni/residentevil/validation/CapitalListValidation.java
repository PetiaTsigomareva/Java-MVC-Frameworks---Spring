package softuni.residentevil.validation;

import softuni.residentevil.cofigurations.Constants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Constraint(validatedBy = {CapitalListValidationImpl.class})
public @interface CapitalListValidation {
    String message() default Constants.INVALID_CAPITAL_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean ignoreCase() default false;
}
