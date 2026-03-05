package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Collection<User> findAll();

    Optional<User> findById(int id);

    User save(User user);

    User update(int id,User user);

    Boolean exist(int id);

}
