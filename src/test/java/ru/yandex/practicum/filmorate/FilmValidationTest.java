package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidationTest {

    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validFilm_hasNoViolations() {
        Film film = new Film(
                1,
                "Matrix",
                "Cool movie",
                LocalDate.of(1999, 3, 31),
                120
        );

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Film should be valid");
    }

    @Test
    void blankName_violation() {
        Film film = new Film(
                1,
                "   ",
                "desc",
                LocalDate.of(2000, 1, 1),
                90
        );

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("name") &&
                                v.getMessage().contains("название")
                ),
                "Expected @NotBlank violation for name"
        );
    }

    @Test
    void descriptionMoreThan200_violation() {
        String longDesc = "a".repeat(201);
        Film film = new Film(
                1,
                "Ok",
                longDesc,
                LocalDate.of(2000, 1, 1),
                90
        );

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("description") &&
                                v.getMessage().contains("не больше 200")
                ),
                "Expected @Size(max=200) violation for description"
        );
    }

    @Test
    void negativeDuration_violation() {
        Film film = new Film(
                1,
                "Ok",
                "desc",
                LocalDate.of(2000, 1, 1),
                -1
        );

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("duration")
                ),
                "Expected @PositiveOrZero violation for duration"
        );
    }

    @Test
    void releaseDateTooEarly_violation_byMinDate() {
        // дата раньше кинематографа (обычно 1895-12-28)
        Film film = new Film(
                1,
                "Ok",
                "desc",
                LocalDate.of(1800, 1, 1),
                90
        );

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("releaseDate")
                ),
                "Expected @MinDate violation for releaseDate"
        );
    }
}