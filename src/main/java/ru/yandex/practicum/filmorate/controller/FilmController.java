package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long counter = 0L;

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid  @RequestBody Film film){
        log.info("Создание фильма {}", film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм {} успешно добавлен", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Обновление пользователя {}", film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Данные фильма {} успешно обновлены", film.getName());
            return film;
        } else {
            log.error("Пользователь {} не найден в базе", film.getName());
            throw new ValidationException(String.format("Фильм %s не найден в базе", film.getName()));
        }
    }

    private long generateId() {
        return ++counter;
    }



}
