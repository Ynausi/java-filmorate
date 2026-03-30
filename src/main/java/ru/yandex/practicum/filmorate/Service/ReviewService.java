package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.Optional;

public interface ReviewService {

    Collection<Review> findAll();

    Review findById(int reviewId);

    Review save(Review review);

    Review update(Review review);

    boolean delete(int reviewId);

    Collection<Review> getReviewsForFilm(int filmId,int count);
}
