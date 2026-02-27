package ru.yandex.practicum.filmorate.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.MyAnnotations.MinDate;

import java.time.LocalDate;

public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {

    private final LocalDate minDate = LocalDate.of(1895,12,28);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            return !value.isBefore(minDate);
        }
    }
}
