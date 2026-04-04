package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;

import java.util.Collection;

public interface EventRepository {
    void addEvent(int userId, EventType eventType, Operation operation, int entityId);

    Collection<Event> getUserEvents(int userId);
}