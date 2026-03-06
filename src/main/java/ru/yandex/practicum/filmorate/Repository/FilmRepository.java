package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {

    Collection<Film> findAll();

    Optional<Film> findById(int id);

    Film save(Film film);

    Film update(int id,Film film);

    Film addLikeToFilm(int filmId,int userId);

    Film deleteLikeFromFilm(int filmId, int userId);

    Collection<Film> getPopularFilms(int count);

    Boolean exist(int id);
}
