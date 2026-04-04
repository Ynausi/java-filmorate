package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Service.EventService;
import ru.yandex.practicum.filmorate.dto.EventResponse;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Loggable(value = "Получение ленты событий пользователя", level = LogLevel.INFO)
    @GetMapping("/{id}/feed")
    public ResponseEntity<Collection<EventResponse>> getUserFeed(@PathVariable("id") int userId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.getUserEvents(userId));
    }
}