package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {

    Collection<Film> findAll();

    Optional<Film> findById(int id);

    Film save(Film film);

    Film update(Film film);

    Collection<Film> getPopularFilms(int count);

    Collection<Film> getDirectorFilmsByLikes(int directorId);

    Collection<Film> getDirectorFilmsByYear(int directorId);

    boolean delete(int filmId);
}