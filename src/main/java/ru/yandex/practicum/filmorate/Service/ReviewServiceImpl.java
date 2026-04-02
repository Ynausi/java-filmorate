package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.FilmRepository;
import ru.yandex.practicum.filmorate.Repository.ReviewRepository;
import ru.yandex.practicum.filmorate.Repository.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    @Override
    public Collection<Review> findAll() {
        return reviewRepository.getAll();
    }

    @Override
    public Review findById(int reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new NotFoundException("No review with reviewId = " + reviewId));
    }

    @Override
    public Review save(Review review) {
        if (filmRepository.findById(review.getFilmId()).isEmpty()) {
            throw new NotFoundException("No Film with filmId = " + review.getFilmId());
        }
        if (userRepository.findById(review.getUserId()).isEmpty()) {
            throw new NotFoundException("No User with userId = " + review.getUserId());
        }

        Review saved = reviewRepository.save(review);

        eventService.addEvent(
                saved.getUserId(),
                EventType.REVIEW,
                Operation.ADD,
                saved.getReviewId()
        );

        return saved;
    }

    @Override
    public Review update(Review review) {
        if (filmRepository.findById(review.getFilmId()).isEmpty()) {
            throw new NotFoundException("No Film with filmId = " + review.getFilmId());
        }
        if (userRepository.findById(review.getUserId()).isEmpty()) {
            throw new NotFoundException("No User with userId = " + review.getUserId());
        }

        Review updated = reviewRepository.update(review).orElseThrow(() ->
                new NotFoundException("No Review with reviewId = " + review.getReviewId()));

        eventService.addEvent(
                updated.getUserId(),
                EventType.REVIEW,
                Operation.UPDATE,
                updated.getReviewId()
        );

        return updated;
    }

    @Override
    public boolean delete(int reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("No review with reviewId = " + reviewId));

        boolean deleted = reviewRepository.delete(reviewId);

        eventService.addEvent(
                review.getUserId(),
                EventType.REVIEW,
                Operation.REMOVE,
                reviewId
        );

        return deleted;
    }

    @Override
    public Collection<Review> getReviewsForFilm(int filmId, int count) {
        return reviewRepository.getReviewsForFilm(filmId, count);
    }

    @Override
    public Review addLikeOrDislike(int reviewId, int userId, String action) {
        boolean isLike;
        if ("like".equals(action)) {
            isLike = true;
        } else if ("dislike".equals(action)) {
            isLike = false;
        } else {
            throw new IllegalArgumentException("Unknown action: " + action);
        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("No Review with reviewId = " + reviewId));

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("No User with userId = " + userId);
        }

        if (isLike) {
            reviewRepository.addLike(review.getReviewId(), userId, 1);
        } else {
            reviewRepository.addLike(review.getReviewId(), userId, -1);
        }

        Review updated = reviewRepository.update(review).orElseThrow(() ->
                new NotFoundException("No Review with reviewId = " + review.getReviewId()));

        return updated;
    }

    @Override
    public Review deleteLikeOrDislike(int reviewId, int userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("No Review with reviewId = " + reviewId));

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("No User with userId = " + userId);
        }

        reviewRepository.deleteLike(review.getReviewId(), userId);

        Review updated = reviewRepository.update(review).orElseThrow(
                () -> new NotFoundException("No Review with reviewId = " + review.getReviewId()));

        return updated;
    }
}