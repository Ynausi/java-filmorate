package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Set<User>> friends = new HashMap<>();

    @Loggable(value = "Получение всех пользователей",level = LogLevel.INFO)
    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @Loggable(value = "Получение пользователя",level = LogLevel.INFO)
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            throw new NotFoundException("Пользователя с id: " + userId + " нет");
        }
    }

    @Loggable(value = "Получение друзей пользователя",level = LogLevel.INFO)
    @GetMapping("/{id}/friends")
    public Set<User> getFriendS(@PathVariable("id") int userId) {
       checkUsers(userId);
       return friends.getOrDefault(userId,Collections.emptySet());
    }

    @Loggable(value = "Получение друга пользователя",level = LogLevel.INFO)
    @GetMapping("/{id}/friends/{frId}")
    public User getFriend(@PathVariable("id") int userId, @PathVariable("frId") int friendId) {
       checkUsers(userId,friendId);
       Set<User> userFriends = friends.getOrDefault(userId,Collections.emptySet());
       return userFriends.stream().filter(u -> u.getId() == friendId).findFirst().orElseThrow(() -> new NotFoundException("Они не друзья"));
    }


    @Loggable(value = "Добавление пользователя",level = LogLevel.INFO)
    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty()
                || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(),user);
        return user;
    }

    @Loggable(value = "Добавление пользователю друга",level  = LogLevel.INFO)
    @PostMapping("/{id}/friends/{frId}")
    public User postUserFriend(@PathVariable("id") int userId,@PathVariable("frId") int friendId) {
        checkUsers(userId,friendId);
        Set<User> notKnow = friends.get(userId);
        notKnow.add(users.get(friendId));
        friends.put(userId,notKnow);
        return users.get(friendId);
    }

    @Loggable(value = "Изменение данных",level = LogLevel.INFO)
    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            if (!oldUser.getEmail().equals(user.getEmail())) {
                oldUser.setEmail(user.getEmail());
            }
            if (oldUser.getBirthday() != null) {
                if (!oldUser.getBirthday().equals(user.getBirthday())) {
                    oldUser.setBirthday(user.getBirthday());
                }
            } else if (user.getBirthday() != null) {
                oldUser.setBirthday(user.getBirthday());
            }
            if (!oldUser.getLogin().equals(user.getLogin())) {
                oldUser.setLogin(user.getLogin());
            }
            if (oldUser.getName() != null) {
                if (!oldUser.getName().equals(user.getName())) {
                    oldUser.setName(user.getName());
                }
            } else if (user.getName() != null) {
                oldUser.setName(user.getName());
            }

            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
    }

    @Loggable(value = "Добавление друга через PUT",level = LogLevel.INFO)
    @PutMapping("/{id}/friends/{frId}")
    public User putUserFriend(@PathVariable("id") int userId,@PathVariable("frId") int friendId) {
        checkUsers(userId,friendId);
        Set<User> notKnow = friends.getOrDefault(userId,new HashSet<>());
        notKnow.add(users.get(friendId));
        friends.put(userId,notKnow);
        return users.get(friendId);
    }

    private void checkUsers(int userId,int friendId) {
        if (!users.containsKey(userId) || !users.containsKey(friendId)) {
            if (!users.containsKey(userId) && users.containsKey(friendId)) {
                throw new NotFoundException("Пользователя с id:" + userId + " не существует");
            } else if (users.containsKey(userId) && !users.containsKey(friendId)) {
                throw new NotFoundException("Пользователя с id:" + friendId + " не существует");
            } else {
                throw new NotFoundException("Обоих пользователей не существует");
            }
        }
    }

    private void checkUsers(int userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("Такого пользователя нет");
        }
    }

    private int getNextId() {
        int currentMaxId =  users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
