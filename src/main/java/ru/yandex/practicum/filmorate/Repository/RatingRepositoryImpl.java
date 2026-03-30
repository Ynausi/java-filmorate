package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.*;

@Repository
public class RatingRepositoryImpl extends BaseRepository<Rating> implements RatingRepository {
    private static final String FIND_BY_Rating = "SELECT * FROM Rating WHERE id = ? ";
    private static final String FIND_ALL_RATING_FOR_FILM = "SELECT r.id,r.name FROM Rating as r " +
            "JOIN Films as f ON r.id = ";
    private static final String FIND_ALL_RATINGS = "SELECT * FROM Rating ORDER BY id";

    public RatingRepositoryImpl(JdbcTemplate jdbc, RowMapper<Rating> mapper) {
        super(jdbc, mapper);
    }

    public Optional<Rating> findById(int id) {
        return findOne(FIND_BY_Rating, id);
    }

    @Override
    public Set<Rating> findAllRatingForFilm(int filmId) {
        Collection<Rating> all = findMany(FIND_ALL_RATING_FOR_FILM, filmId);
        return new LinkedHashSet<>(all);
    }

    @Override
    public Set<Rating> findAllRating() {
        return new LinkedHashSet<>(findMany(FIND_ALL_RATINGS));
    }
}