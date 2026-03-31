package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Service.DirectorImpl;
import ru.yandex.practicum.filmorate.Service.DirectorService;
import ru.yandex.practicum.filmorate.model.Director;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @Loggable(value = "Получение всех режиссёров", level = LogLevel.INFO)
    @GetMapping
    public ResponseEntity<Collection<Director>> getAll() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(directorService.findAll());
    }

    @Loggable(value = "Получение режиссёра по id", level = LogLevel.INFO)
    @GetMapping("/{id}")
    public ResponseEntity<Director> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(directorService.findById(id));
    }

    @Loggable(value = "Добавление режиссёра", level = LogLevel.INFO)
    @PostMapping
    public ResponseEntity<Director> createDirector(@RequestBody Director director) {
        Director created = directorService.save(director);
        return ResponseEntity.created(URI.create("/directors/" + created.getId()))
                .body(created);
    }

    @Loggable(value = "Изменение данных режиссёра", level = LogLevel.INFO)
    @PutMapping
    public ResponseEntity<Director> updateDirector(@RequestBody Director director) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(directorService.update(director));
    }

    @Loggable(value = "Удаление режиссёра", level = LogLevel.INFO)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable("id") int id) {
        return ResponseEntity.ok()
                .body(directorService.delete(id));
    }
}