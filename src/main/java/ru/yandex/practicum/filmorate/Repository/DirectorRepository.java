package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Director;
import java.util.Optional;
import java.util.Set;

public interface DirectorRepository {

    Set<Director> findAll();

    Set<Director> findAllDirectorsForFilm(int filmId);

    Optional<Director> findById(int directorId);

    Director save(Director director);

    Director update(Director director);

    boolean delete(int directorId);
}
