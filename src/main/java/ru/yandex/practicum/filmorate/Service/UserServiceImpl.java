package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
        if (!userRepository.exist(id)) {
            throw new NotFoundException("Пользователь с id:" + id + "не найден");
        }
        return userRepository.update(id,user);
    }

    @Override
    public User addFriend(int firstUserId, int secondUserId) {
        User firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + firstUserId + "не найден"));
        User secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + secondUserId + "не найден"));
        userRepository.addFriend(firstUserId,secondUserId);
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
        Set<Integer> firstUserFriends = firstUser.getFriends();
        userRepository.deleteFriend(firstUserId,secondUserId);
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
    public Collection<User> getCommonFriends(int firstUserId,int secondUserId) {
        User firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + firstUserId + "не найден"));
        User secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + secondUserId + "не найден"));
        return userRepository.getCommonFriends(firstUserId,secondUserId);
    }

    @Override
    public Boolean exist(int id) {
        return userRepository.exist(id);
    }


}
