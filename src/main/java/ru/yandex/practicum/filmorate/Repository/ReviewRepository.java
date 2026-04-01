package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.Optional;

public interface ReviewRepository {

    Collection<Review> getAll();

    Optional<Review> findById(int reviewId);

    Review save(Review review);

    Optional<Review> update(Review review);

    boolean delete(int reviewId);

    Collection<Review> getReviewsForFilm(int filmId,int count);

    void addLike(int reviewId, int userId, int useful);

    void deleteLike(int reviewId, int userId);

    void updateUseful(int reviewId);
}
