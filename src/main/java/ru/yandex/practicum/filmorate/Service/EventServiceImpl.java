package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Repository.EventRepository;
import ru.yandex.practicum.filmorate.Repository.UserRepository;
import ru.yandex.practicum.filmorate.dto.EventResponse;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    @Loggable(value = "Adding event", level = LogLevel.DEBUG)
    public void addEvent(int userId, EventType eventType, Operation operation, int entityId) {
        eventRepository.addEvent(userId, eventType, operation, entityId);
    }

    @Override
    @Loggable(value = "Getting events for user", level = LogLevel.DEBUG)
    public Collection<EventResponse> getUserEvents(int userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователя с id: " + userId + " не существует");
        }
        return eventRepository.getUserEvents(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private EventResponse convertToResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setEventId(event.getId());
        response.setUserId(event.getUserId());
        response.setEventType(event.getEventType());
        response.setOperation(event.getOperation());
        response.setEntityId(event.getEntityId());
        response.setTimestamp(event.getTimestamp());
        return response;
    }
}