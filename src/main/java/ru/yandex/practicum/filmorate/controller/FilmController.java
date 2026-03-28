package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Service.FilmService;
import ru.yandex.practicum.filmorate.dto.FilmRequest;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import ru.yandex.practicum.filmorate.model.Film;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @Loggable(value = "Получение всех фильмов",level = LogLevel.INFO)
    @GetMapping
    public ResponseEntity<Collection<?>> getAll() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(filmService.findAll());
    }


    @Loggable(value = "Получение фильма",level = LogLevel.INFO)
    @GetMapping("/{id}")
    public FilmResponse getById(@PathVariable("id") int filmId) {
        return filmService.findById(filmId);
    }

    @Loggable(value = "Получить список популярных фильмов",level = LogLevel.INFO)
    @GetMapping("/popular")
    public ResponseEntity<Collection<FilmResponse>> getPopularFilms(
            @RequestParam(name = "count",defaultValue = "10") int count) {
        return ResponseEntity.ok(filmService.getPopularFilms(count));
    }

    @Loggable(value = "Добавление фильма",level = LogLevel.INFO)
    @PostMapping
    public ResponseEntity<FilmResponse> createFilm(@Valid @RequestBody FilmRequest film) {
        FilmResponse created = filmService.save(film);
        return ResponseEntity.created(URI.create("/films/" + created.getId()))
                .body(created);
    }

    @Loggable(value = "Изменение данных",level = LogLevel.INFO)
    @PutMapping()
    public ResponseEntity<FilmResponse> putFilm(@Valid @RequestBody FilmRequest film) {
        return ResponseEntity.ok(filmService.update(film));
    }

    @Loggable(value = "Добавление лайка", level = LogLevel.INFO)
    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLikeToFilm(@PathVariable("id") int filmId,
                              @PathVariable("userId") int userId) {
        return ResponseEntity.ok(filmService.addLikeToFilm(filmId,userId));
    }

    @Loggable(value = "Удаление лайка", level = LogLevel.INFO)
    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> deleteLike(@PathVariable("id") int filmId,
                           @PathVariable("userId") int userId) {
        return ResponseEntity.ok(filmService.deleteLikeFromFilm(filmId,userId));
    }

    @Loggable(value = "Получить список популярных фильмов",level = LogLevel.INFO)
    @GetMapping("/directors/{directorId}")
    public ResponseEntity<Collection<FilmResponse>> getDirectorFilmsSortedByYearOrLikes(
            @PathVariable("directorId") int directorId,
            @RequestParam(name = "sortBy") String sortBy) {
        return ResponseEntity.ok(filmService.getDirectorFilmsByLikesOrYear(directorId,sortBy));
    }
}
