package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Service.UserService;
import ru.yandex.practicum.filmorate.model.User;

import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Loggable(value = "Получение всех пользователей",level = LogLevel.INFO)
    @GetMapping
    public Collection<User> getUsers() {
        return userService.findAll();
    }

    @Loggable(value = "Получение пользователя",level = LogLevel.INFO)
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int userId) {
        return userService.findById(userId);
    }

    @Loggable(value = "Добавление пользователя",level = LogLevel.INFO)
    @PostMapping
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.created(URI.create("/users/" + created.getId()))
                .body(created);
    }


    @Loggable(value = "Изменение данных",level = LogLevel.INFO)
    @PutMapping
    public User putUser(@PathVariable int id,@Valid @RequestBody User user) {
        return userService.update(id,user);
    }

}
