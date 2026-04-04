package ru.yandex.practicum.filmorate.Repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import java.util.Collection;

import java.util.List;

@Slf4j
@Repository
public class EventRepositoryImpl extends BaseRepository<Event> implements EventRepository {
    private static final String ADD_EVENT = "INSERT INTO Event(user_id, event_type, operation, entity_id, timestamp) " +
            "VALUES(?, ?, ?, ?, ?)";
    private static final String GET_USER_EVENTS = "SELECT * FROM Event WHERE user_id = ? ORDER BY timestamp ASC,id ASC";

    public EventRepositoryImpl(JdbcTemplate jdbc, RowMapper<Event> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void addEvent(int userId, EventType eventType, Operation operation, int entityId) {
        long timestamp = System.currentTimeMillis();
        update(ADD_EVENT, userId, eventType.name(), operation.name(), entityId, timestamp);
    }

    @Override
    public Collection<Event> getUserEvents(int userId) {
        return findMany(GET_USER_EVENTS, userId);
    }
}