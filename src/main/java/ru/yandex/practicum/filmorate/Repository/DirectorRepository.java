package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.Collection;
import java.util.Optional;

public interface DirectorRepository {

    Collection<Director> findAll();

    Optional<Director> findById(int directorId);

    Director save(Director director);

    Director update(Director director);

    boolean delete(int directorId);
}