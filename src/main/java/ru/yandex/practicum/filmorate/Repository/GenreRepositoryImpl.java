package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

@Repository
public class GenreRepositoryImpl extends BaseRepository<Genre> implements GenreRepository {
    private static final String FIND_ALL_GENRES_FOR_FILM =
            "SELECT g.id, g.name FROM Genre AS g " +
                    "JOIN Film_Genre fg ON g.id = fg.genreId " +
                    "WHERE fg.filmId = ? " +
                    "ORDER BY g.id";
    private static final String FIND_ALL_GENRES = "SELECT * FROM Genre AS g " +
            "ORDER BY g.id";
    private static final String FIND_BY_ID = "SELECT * FROM Genre AS g " +
            "WHERE id = ? " +
            "ORDER BY g.id";

    public GenreRepositoryImpl(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Optional<Genre> findById(int id) {
        return findOne(FIND_BY_ID, id);
    }

    @Override
    public Set<Genre> findAllGenresForFilm(int filmId) {
        return new LinkedHashSet<>(findMany(FIND_ALL_GENRES_FOR_FILM, filmId));
    }

    @Override
    public Set<Genre> findAllGenres() {
        return new LinkedHashSet<>(findMany(FIND_ALL_GENRES));
    }


}