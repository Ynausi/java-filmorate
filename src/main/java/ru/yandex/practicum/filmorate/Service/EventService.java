package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.dto.EventResponse;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import java.util.Collection;


public interface EventService {
    void addEvent(int userId, EventType eventType, Operation operation, int entityId);

    Collection<EventResponse> getUserEvents(int userId);
}