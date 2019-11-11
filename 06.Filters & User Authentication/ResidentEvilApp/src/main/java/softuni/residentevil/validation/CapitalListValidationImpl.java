package softuni.residentevil.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CapitalListValidationImpl implements ConstraintValidator<CapitalListValidation, List> {
    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        boolean result=false;
        if (value.size() != 0) {
            result = true;
        }

        return result;
    }
}
