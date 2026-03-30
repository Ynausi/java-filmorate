package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.FilmRepository;
import ru.yandex.practicum.filmorate.Repository.ReviewRepository;
import ru.yandex.practicum.filmorate.Repository.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
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
            throw new NotFoundException("No Film with filmId = "+ review.getFilmId());
        }
        if(userRepository.findById(review.getUserId()).isEmpty()) {
            throw new NotFoundException("No User with userId = " + review.getUserId());
        }
        if (review.getIsPositive()) {
            review.setUseful(+1);
        } else {
            review.setUseful(-1);
        }
        return reviewRepository.save(review);
    }

    @Override
    public Review update(Review review) {
        if (reviewRepository.findById(review.getReviewId()).isEmpty()) {
            throw new NotFoundException("No Review with filmId = "+ review.getReviewId());
        }
        if (filmRepository.findById(review.getFilmId()).isEmpty()) {
            throw new NotFoundException("No Film with filmId = "+ review.getFilmId());
        }
        if (userRepository.findById(review.getUserId()).isEmpty()) {
            throw new NotFoundException("No User with userId = " + review.getUserId());
        }
        Review old = reviewRepository.findById(review.getReviewId()).orElseThrow(() ->
                new NotFoundException("No review with reviewId = " + review.getReviewId()));;
        if (review.getIsPositive()) {
            review.setUseful(old.getUseful()+1);
        } else {
            review.setUseful(old.getUseful()-1);
        }
        return reviewRepository.update(review);
    }

    @Override
    public boolean delete(int reviewId) {
        return reviewRepository.delete(reviewId);
    }

    @Override
    public Collection<Review> getReviewsForFilm(int filmId,int count) {
        return reviewRepository.getReviewsForFilm(filmId,count);
    }
}
