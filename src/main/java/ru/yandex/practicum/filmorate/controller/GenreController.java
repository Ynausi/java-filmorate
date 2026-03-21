package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Service.GenreService;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.Collection;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @Loggable(value = "Получение фильмов c жанрами",level = LogLevel.INFO)
    @GetMapping
    public ResponseEntity<Collection<?>> getAll() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(genreService.findAllFilmsWithGenre());
    }


    @Loggable(value = "Получение жанра по id",level = LogLevel.INFO)
    @GetMapping("/{id}")
    public Genre getById(@PathVariable("id") int genreId) {
        return genreService.findById(genreId);
    }
}
