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
    private final UserService userService;

    @Loggable(value = "Получение всех пользователей", level = LogLevel.INFO)
    @GetMapping
    public Collection<User> getUsers() {
        return userService.findAll();
    }

    @Loggable(value = "Получение друзей пользователя", level = LogLevel.INFO)
    @GetMapping("/{id}/friends")
    public ResponseEntity<Collection<User>> getUserFriends(@PathVariable("id") int userId) {
        return ResponseEntity.ok(userService.getUserFriends(userId));
    }

    @Loggable(value = "Получение пользователя", level = LogLevel.INFO)
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int userId) {
        return userService.findById(userId);
    }

    @Loggable(value = "Получение общих друзей с другим пользователем", level = LogLevel.INFO)
    @GetMapping("/{id}/friends/common/{friendId}")
    public ResponseEntity<Collection<User>> getCommonFriends(@PathVariable("id") int id,
                                                             @PathVariable("friendId") int friendId) {
        return ResponseEntity.ok(userService.getCommonFriends(id, friendId));
    }

    @Loggable(value = "Добавление пользователя", level = LogLevel.INFO)
    @PostMapping
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.created(URI.create("/users/" + created.getId()))
                .body(created);
    }

    @Loggable(value = "Изменение данных", level = LogLevel.INFO)
    @PutMapping()
    public ResponseEntity<User> putUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.update(user.getId(), user));
    }

    @Loggable(value = "Добавление в друзья", level = LogLevel.INFO)
    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> addUserFriend(@PathVariable("id") int userId,
                                              @PathVariable("friendId") int friendId) {
        return ResponseEntity.ok(userService.addFriend(userId, friendId));
    }

    @Loggable(value = "Удаление из друзей", level = LogLevel.INFO)
    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> deleteUserFriend(@PathVariable("id") int userId,
                                                 @PathVariable("friendId") int friendId) {
        return ResponseEntity.ok(userService.deleteFriend(userId, friendId));
    }

    @Loggable(value = "Удаление пользователя по id", level = LogLevel.INFO)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}