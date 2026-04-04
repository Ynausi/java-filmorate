package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Service.ReviewService;
import ru.yandex.practicum.filmorate.model.Review;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Loggable(value = "Получение всех отзывов", level = LogLevel.INFO)
    @GetMapping
    public ResponseEntity<Collection<Review>> getAll() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reviewService.findAll());
    }

    @Loggable(value = "Получение отзыва по id", level = LogLevel.INFO)
    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reviewService.findById(id));
    }

    @Loggable(value = "Добавление отзыва", level = LogLevel.INFO)
    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
        Review created = reviewService.save(review);
        return ResponseEntity.created(URI.create("/" + created.getReviewId()))
                .body(created);
    }

    @Loggable(value = "Изменение данных отзыва", level = LogLevel.INFO)
    @PutMapping
    public ResponseEntity<?> updateReview(@Valid @RequestBody Review review) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reviewService.update(review));
    }

    @Loggable(value = "Удаление отзыва", level = LogLevel.INFO)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") int id) {
        reviewService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @Loggable(value = "Получение отзывов для фильма", level = LogLevel.INFO)
    @GetMapping(params = "filmId")
    public ResponseEntity<Collection<Review>> getReviewsForFilm(@RequestParam(name = "filmId") int filmId,
                                                                @RequestParam(name = "count", defaultValue = "10") int count) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reviewService.getReviewsForFilm(filmId, count));
    }

    @Loggable(value = "Добавление лайка для отзыва", level = LogLevel.INFO)
    @PutMapping("/{id}/{action}/{userId}")
    public ResponseEntity<Review> addLikeToReview(@PathVariable("id") int reviewId,
                                                  @PathVariable("action") String action,
                                                  @PathVariable("userId") int userId) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reviewService.addLikeOrDislike(reviewId, userId, action));
    }


    @Loggable(value = "Добавление лайка для отзыва", level = LogLevel.INFO)
    @DeleteMapping("/{id}/{action}/{userId}")
    public ResponseEntity<Review> deleteLikeToReview(@PathVariable("id") int reviewId,
                                                     @PathVariable("action") String action,
                                                     @PathVariable("userId") int userId) {
        if (!"like".equals(action) && !"dislike".equals(action)) {
            throw new IllegalArgumentException("Unknown action: " + action);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reviewService.deleteLikeOrDislike(reviewId, userId));
    }
}