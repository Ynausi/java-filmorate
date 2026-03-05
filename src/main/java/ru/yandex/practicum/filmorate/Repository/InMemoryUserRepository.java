package ru.yandex.practicum.filmorate.Repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private Map<Integer, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll(){
        return users.values();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User save(User User) {
        User.setId(getNextId());
        users.put(User.getId(),User);
        return User;
    }

    @Override
    public User update(int id,User User) {
        User oldUser = users.get(id);
        oldUser = User;
        users.replace(id,oldUser);
        return oldUser;
    }

    public Boolean exist(int id) {
        return users.containsKey(id);
    }

    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
