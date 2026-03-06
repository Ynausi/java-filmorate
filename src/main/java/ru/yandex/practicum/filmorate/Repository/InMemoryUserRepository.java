package ru.yandex.practicum.filmorate.Repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<Integer, User> users = new HashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User save(User user) {
        int id = atomicInteger.incrementAndGet();
        user.setId(id);
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public User update(int id,User user) {
        User oldUser = users.get(id);
        oldUser = user;
        users.replace(id,oldUser);
        return oldUser;
    }

    @Override
    public User addFriend(int userId, int friendId) {
        User firstUser = (users.get(userId));
        User secondUser = (users.get(friendId));
        Set<Integer> firstUserFriends = firstUser.getFriends();
        Set<Integer> secondUserFriends = secondUser.getFriends();
        firstUserFriends.add(friendId);
        secondUserFriends.add(userId);
        firstUser.setFriends(firstUserFriends);
        secondUser.setFriends(secondUserFriends);
        users.put(userId,firstUser);
        users.put(friendId,secondUser);
        return firstUser;
    }

    @Override
    public User deleteFriend(int userId, int friendId) {
        User firstUser = (users.get(userId));
        User secondUser = (users.get(friendId));
        Set<Integer> firstUserFriends = firstUser.getFriends();
        Set<Integer> secondUserFriends = secondUser.getFriends();
        firstUserFriends.remove(friendId);
        secondUserFriends.remove(userId);
        firstUser.setFriends(firstUserFriends);
        secondUser.setFriends(secondUserFriends);
        users.put(userId,firstUser);
        users.put(friendId,secondUser);
        return firstUser;
    }

    @Override
    public Collection<User> getUserFriends(int userId) {
        return users.get(userId).getFriends().stream()
                .map(id -> users.get(id))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(int userId,int friendId) {
        Set<Integer> secondUserFriends = users.get(friendId).getFriends();
        return users.get(userId).getFriends().stream()
                .filter(secondUserFriends::contains)
                .map(id -> users.get(id)).collect(Collectors.toList());
    }

    public Boolean exist(int id) {
        return users.containsKey(id);
    }
}
