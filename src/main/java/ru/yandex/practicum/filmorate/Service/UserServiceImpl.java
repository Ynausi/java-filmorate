package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
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
    public Boolean exist(int id) {
        return userRepository.exist(id);
    }


}
