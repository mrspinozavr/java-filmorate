package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

class UserTest {

    private final User user = User
            .builder()
            .login("a_b")
            .name("Alexandr Osiptsov")
            .email("aosiptsov@mail.ru")
            .birthday(LocalDate.of(1985, 9,30))
            .build();
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void shouldCreateUser() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateUserIfLoginIsWrong() {
        String[] logins = {"dolore ullamco", "d olore ullamc o", "", " ", null};

        Arrays.stream(logins).forEach(login -> {
            User userWithIncorrectLogin = User
                    .builder()
                    .login(login)
                    .build();

            Set<ConstraintViolation<User>> violations = validator.validate(userWithIncorrectLogin);

            Assertions.assertFalse(violations.isEmpty());
        });
    }

    @Test
    void shouldNotCreateUserIfEmailIsWrong() {
        String[] emails = {"user @domain.com", ".user@domain.co.in", "@domain.com", "user?name@doma in.co.in",
                "@domain.com",
                "",
                null};

        Arrays.stream(emails).forEach(email -> {
            User userWithIncorrectEmail = User
                    .builder()
                    .email(email)
                    .build();

            Set<ConstraintViolation<User>> violations = validator.validate(userWithIncorrectEmail);

            Assertions.assertFalse(violations.isEmpty());
        });
    }

    @Test
    void shouldNotCreateUserIfBirthdayIsWrong() {
        user.setBirthday(LocalDate.now());

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }
}