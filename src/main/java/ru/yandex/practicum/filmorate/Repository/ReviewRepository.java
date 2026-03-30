package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.Optional;

public interface ReviewRepository {

    Collection<Review> getAll();

    Optional<Review> findById(int reviewId);

    Review save(Review review);

    Review update(Review review);

    boolean delete(int reviewId);

    Collection<Review> getReviewsForFilm(int filmId,int count);
}
