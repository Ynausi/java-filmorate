package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.FilmDirector;

import java.util.Optional;

public interface FilmDirectorsRepository {

    void addDirectorToFilm(int filmId, int directorId);

    Optional<FilmDirector> findById(int id);

    void deleteDirectorsFromFilm(int filmId);
}