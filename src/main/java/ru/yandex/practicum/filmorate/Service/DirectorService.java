package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.Collection;
import java.util.Optional;

public interface DirectorService {

    Collection<Director> findAll();

    Director findById(int directorId);

    Director save(Director director);

    Director update(Director director);

    boolean delete(int directorId);
}