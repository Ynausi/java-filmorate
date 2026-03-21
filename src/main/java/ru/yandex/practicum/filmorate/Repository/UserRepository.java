package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Collection<User> findAll();

    Optional<User> findById(int id);

    User save(User user);

    User update(User user);

    Collection<User> getUserFriends(int userId);

    Collection<User> getCommonFriends(int userId,int friendId);


}
