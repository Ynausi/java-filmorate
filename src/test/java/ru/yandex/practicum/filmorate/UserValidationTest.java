package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;


class UserValidationTest {

    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validUser_hasNoViolations() {
        User user = new User(
                1,
                "user@mail.com",
                "login",
                "Name",
                LocalDate.of(2000, 1, 1),Set.of(1,2)
        );

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "User should be valid");
    }

    @Test
    void invalidEmail_violation() {
        User user = new User(
                1,
                "not-an-email",
                "login",
                "Name",
                LocalDate.of(2000, 1, 1),Set.of(1,2)
        );

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("email") &&
                                v.getMessage().contains("email")
                ),
                "Expected @Email violation for email"
        );
    }

    @Test
    void blankEmail_violation() {
        User user = new User(
                1,
                "   ",
                "login",
                "Name",
                LocalDate.of(2000, 1, 1),Set.of(1,2)
        );

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("email")
                ),
                "Expected @NotBlank violation for email"
        );
    }

    @Test
    void loginWithSpaces_violation() {
        User user = new User(
                1,
                "user@mail.com",
                "lo gin",
                "Name",
                LocalDate.of(2000, 1, 1),Set.of(1,2)
        );

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("login") &&
                                v.getMessage().contains("пробел")
                ),
                "Expected @Pattern violation for login"
        );
    }

    @Test
    void blankLogin_violation() {
        User user = new User(
                1,
                "user@mail.com",
                "   ",
                "Name",
                LocalDate.of(2000, 1, 1),Set.of(1,2)
        );

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("login")
                ),
                "Expected @NotBlank violation for login"
        );
    }

    @Test
    void futureBirthday_violation() {
        User user = new User(
                1,
                "user@mail.com",
                "login",
                "Name",
                LocalDate.now().plusDays(1),Set.of(1,2)
        );

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals("birthday") &&
                                v.getMessage().contains("дата")
                ),
                "Expected @PastOrPresent violation for birthday"
        );
    }
}