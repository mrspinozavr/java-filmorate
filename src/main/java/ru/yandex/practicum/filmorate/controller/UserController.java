package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Marker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private long counter = 0L;

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    @Validated(Marker.onCreate.class)
    public User create(@Valid @RequestBody User user) {
        log.info("Создание пользователя {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь {} успешно добавлен", user.getName());
        return user;
    }

    @PutMapping
    @Validated(Marker.onUpdate.class)
    public User update(@Valid @RequestBody  User user) {
        log.info("Обновление пользователя {}", user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Данные пользователя {} успешно обновлены", user.getName());
            return user;
        } else {
            log.error("Пользователь {} не найден в базе", user.getName());
            throw new ValidationException(String.format("Пользователь %s не найден в базе", user.getName()));
        }
    }

    private long generateId() {
        return ++counter;
    }
}
