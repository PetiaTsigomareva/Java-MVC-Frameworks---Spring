package softuni.residentevil.validation;

import softuni.residentevil.cofigurations.Constants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Constraint(validatedBy = {EnumCreatorValidationImpl.class})
public @interface CreatorEnumValidation {
    String message() default Constants.INVALID_CREATOR_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends java.lang.Enum<?>> enumClass();
    boolean ignoreCase() default false;

}
