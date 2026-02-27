package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();

    @Loggable(value = "Получение всех фильмов",level = LogLevel.INFO)
    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }


    @Loggable(value = "Получение фильма",level = LogLevel.INFO)
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") int filmId) {
        if (films.containsKey(filmId)) {
            return films.get(filmId);
        } else {
            throw new NotFoundException("Фильма с id: " + filmId + " нет");
        }
    }

    @Loggable(value = "Добавление фильма",level = LogLevel.INFO)
    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Loggable(value = "Изменение данных",level = LogLevel.INFO)
    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            Film oldFilm = films.get(film.getId());
            if (oldFilm.getDescription() != null && film.getDescription() != null) {
                if (!oldFilm.getDescription().equals(film.getDescription())) {
                    oldFilm.setDescription(film.getDescription());
                }
            } else if (oldFilm.getDescription() == null && film.getDescription() != null) {
                oldFilm.setDescription(film.getDescription());
            }
            if (oldFilm.getDuration() != film.getDuration()) {
                oldFilm.setDuration(film.getDuration());
            }
            if (oldFilm.getReleaseDate() != null && film.getReleaseDate() != null) {
                if (!oldFilm.getReleaseDate().equals(film.getReleaseDate())) {
                    oldFilm.setReleaseDate(film.getReleaseDate());
                }
            } else if (oldFilm.getReleaseDate() == null && film.getReleaseDate() != null) {
                oldFilm.setReleaseDate(film.getReleaseDate());
            }
            if (!oldFilm.getName().equals(film.getName())) {
                oldFilm.setName(film.getName());
            }
            return oldFilm;
        }
        throw new NotFoundException("Фильм с id = " + film.getId() + " не найден");
    }

    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
