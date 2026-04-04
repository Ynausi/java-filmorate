package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.dto.EventResponse;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;

import java.util.List;

public interface EventService {
    void addEvent(int userId, EventType eventType, Operation operation, int entityId);

    List<EventResponse> getUserEvents(int userId);
}