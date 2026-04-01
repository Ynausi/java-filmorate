package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.model.Director;
import java.util.Collection;

public interface DirectorService {

    Collection<Director> findAll();

    Director findById(int directorId);

    Director save(Director director);

    Director update(Director director);

    boolean delete(int directorId);
}