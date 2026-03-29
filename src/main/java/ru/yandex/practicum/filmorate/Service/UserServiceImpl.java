package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.FriendshipRepository;
import ru.yandex.practicum.filmorate.Repository.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.InternalServerException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + id + "не найден"));
    }

    @Override
    public User save(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userRepository.save(user);
    }

    @Override
    public User update(int id, User user) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Пользователь с id:" + id + "не найден");
        }
        return userRepository.update(user);
    }

    @Override
    public User addFriend(int firstUserId, int secondUserId) {
        User firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + firstUserId + "не найден"));
        User secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + secondUserId + "не найден"));
        FriendStatus friendStatus = FriendStatus.CONFIRMED;
        friendshipRepository.addFriendShip(firstUserId, secondUserId, friendStatus);
        return firstUser;
    }

    @Override
    public User deleteFriend(int firstUserId, int secondUserId) {
        User firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + firstUserId + "не найден"));
        User secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + secondUserId + "не найден"));
        friendshipRepository.deleteFriendShip(firstUserId, secondUserId);
        return firstUser;
    }

    @Override
    public Collection<User> getUserFriends(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + userId + "не найден"));
        return userRepository.getUserFriends(userId);
    }

    @Override
    public Collection<User> getCommonFriends(int firstUserId, int secondUserId) {
        User firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + firstUserId + "не найден"));
        User secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + secondUserId + "не найден"));
        return userRepository.getCommonFriends(firstUserId, secondUserId);
    }

    @Override
    public void delete(int userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователя с id: " + userId + " не существует");
        }
        boolean deleted = userRepository.delete(userId);
        if (!deleted) {
            throw new InternalServerException("Не удалось удалить пользователя с id: " + userId);
        }
    }
}