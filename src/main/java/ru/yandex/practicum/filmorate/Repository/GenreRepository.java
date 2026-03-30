package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Optional;
import java.util.Set;

public interface GenreRepository {

    Optional<Genre> findById(int id);

    Set<Genre> findAllGenresForFilm(int filmId);

    Set<Genre> findAllGenres();
}