package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;

public interface UserService {

    Collection<User> findAll();

    User findById(int id);

    User save(User user);

    User update(int id,User user);

    User addFriend(int firstUserId,int secondUserId);

    User deleteFriend(int firstUserId,int secondUserId);

    Collection<User> getUserFriends(int userId);

    Collection<User> getCommonFriends(int firstUserId,int secondUserId);

}
