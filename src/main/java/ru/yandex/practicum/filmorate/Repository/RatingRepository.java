package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Optional;
import java.util.Set;

public interface RatingRepository {

    Optional<Rating> findById(int id);

    Set<Rating> findAllRatingForFilm(int filmId);

    Set<Rating> findAllRating();
}