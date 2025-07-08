package bg.softuni.mobilelele.model.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValue annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty() || value.isBlank()) return false;

        return Arrays.stream(enumClass.getEnumConstants())
            .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
