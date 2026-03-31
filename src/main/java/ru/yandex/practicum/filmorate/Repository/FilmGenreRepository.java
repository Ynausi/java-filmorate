package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.Optional;

public interface FilmGenreRepository {

    void addGenreToFilm(int filmId, int genreId);

    Optional<FilmGenre> findById(int id);

    void deleteAllGenresForFilm(int filmId);
}