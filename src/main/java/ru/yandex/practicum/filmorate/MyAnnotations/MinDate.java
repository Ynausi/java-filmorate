package ru.yandex.practicum.filmorate.MyAnnotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.Validators.MinDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MinDateValidator.class)
public @interface MinDate {
    String message() default "Фильмы тогда ещё не создали";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
