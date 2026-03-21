package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Service.MpaService;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @Loggable(value = "Получение фильмов c рейтингом",level = LogLevel.INFO)
    @GetMapping
    public ResponseEntity<Collection<?>> getAll() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(mpaService.findAllRatings());
    }


    @Loggable(value = "Получение рейтинга по id",level = LogLevel.INFO)
    @GetMapping("/{id}")
    public Rating getById(@PathVariable("id") int ratingId) {
        return mpaService.findById(ratingId);
    }
}
