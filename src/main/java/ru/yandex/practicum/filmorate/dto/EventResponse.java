package ru.yandex.practicum.filmorate.dto;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;

@Data
public class EventResponse {
    private Long timestamp;
    private Integer userId;
    private EventType eventType;
    private Operation operation;
    private Integer eventId;
    private Integer entityId;
}