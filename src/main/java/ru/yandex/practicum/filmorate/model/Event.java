package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Integer id;
    private Integer userId;
    private EventType eventType;
    private Operation operation;
    private Integer entityId;
    private Long timestamp;
}