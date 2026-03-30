package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.dto.FilmRequest;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;

public interface FilmService {

    Collection<FilmResponse> findAll();

    FilmResponse save(FilmRequest film);

    FilmResponse update(FilmRequest film);

    FilmResponse findById(int id);

    Film addLikeToFilm(int filmId,int userId);

    Film deleteLikeFromFilm(int filmId, int userId);

    Collection<FilmResponse> getPopularFilms(int count, Integer genreId, Integer year);

    Collection<FilmResponse> getDirectorFilmsByLikesOrYear(int directorId,String sortBy);
}
