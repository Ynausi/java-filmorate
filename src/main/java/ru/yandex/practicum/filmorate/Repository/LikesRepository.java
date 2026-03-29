package ru.yandex.practicum.filmorate.Repository;

public interface LikesRepository {

    void addLikeToFilm(int filmId, int userId);

    void deleteLikeFromFilm(int filmId, int userId);
}