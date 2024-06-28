package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

class FilmTest {

    private final Film film = Film.builder()
            .name("Game of Thrones")
            .description("Best series ever")
            .releaseDate(LocalDate.of(2011, 4, 17))
            .duration(100)
            .build();
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void shouldCreateFilm() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldCreateFilmWithFirstFilmDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfNameIsEmpty() {
        String[] names = {"", " ", "  ", null};

        Arrays.stream(names).forEach(name -> {
            Film filmWithIncorrectName = Film
                    .builder()
                    .name(name)
                    .description("Best series ever")
                    .releaseDate(LocalDate.of(2011, 4, 17))
                    .duration(100)
                    .build();

            Set<ConstraintViolation<Film>> violations = validator.validate(filmWithIncorrectName);

            Assertions.assertFalse(violations.isEmpty());
        });
    }

    @Test
    void shouldNotCreateFilmIfDescriptionTooLong() {
        film.setDescription("a".repeat(200 + 1));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void shouldNotCreateFilmIfReleaseDateIsWrong() {
        film.setReleaseDate(LocalDate.of(1895, 12 ,27));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void shouldNotCreateFilmIfDurationIsWrong() {
        Integer[] durations = {-100, 201, null};
        film.setDuration(-100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }
}