package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryFilmRepository extends BaseRepository<Film> implements FilmRepository {
    private static final String FIND_ALL_FILMS = "SELECT * FROM Films";
    private static final String FIND_BY_ID = "SELECT * FROM Films WHERE id = ? ";
    private static final String PUT_FILM = "INSERT INTO Films(name,description,releaseDate,ratingId,duration,directorId) " +
            "VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_FILM = "UPDATE Films SET " +
            "name = ?, description = ?,releaseDate = ?,ratingId = ?,duration = ?,directorId = ? " +
            "WHERE id = ?";

    private static final String DELETE_FILM = "DELETE FROM Films WHERE id = ?";

    public InMemoryFilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Film> findAll() {
        return findMany(FIND_ALL_FILMS);
    }

    @Override
    public Optional<Film> findById(int id) {
        return findOne(FIND_BY_ID, id);
    }

    @Override
    public Film save(Film film) {
        int id = insertAndReturnId(PUT_FILM,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getRatingId(),
                film.getDuration(),
                film.getDirectorId()
        );
        film.setId(id);
        return film;
    }

    @Override
    public Film update(Film film) {
        update(UPDATE_FILM,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getRatingId(),
                film.getDuration(),
                film.getDirectorId(),
                film.getId()
        );
        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        final String GET_POPULAR_FILMS =
                "SELECT f.*,COALESCE(l.likes_count,0) AS likes_count " +
                        "FROM Films f " +
                        "LEFT JOIN (" +
                        "SELECT filmId, COUNT(*) as likes_count " +
                        "FROM Likes " +
                        "GROUP BY filmId " +
                        ") l ON f.id = l.filmId " +
                        "ORDER BY likes_count DESC " +
                        "LIMIT " + count;
        return findMany(GET_POPULAR_FILMS);
    }

    @Override
    public Collection<Film> getDirectorFilmsByLikes(int directorId) {
        final String GET_DIRECTORS_FILMS_BY_LIKES =
                "SELECT *,COALESCE(l.likes_count,0) AS likes_count " +
                        "FROM Films " +
                        "LEFT JOIN(" +
                        "SELECT filmId, COUNT(*) as likes_count " +
                        "FROM Likes " +
                        "GROUP BY filmId " +
                        ") l ON f.id = l.filmId " +
                        "WHERE directorId = ? " +
                        "ORDER BY likes_count DESC";
        return findMany(GET_DIRECTORS_FILMS_BY_LIKES);
    }

    @Override
    public Collection<Film> getDirectorFilmsByYear(int directorId) {
        return List.of();
    }

    @Override
    public boolean delete(int filmId) {
        return delete(DELETE_FILM, filmId);
    }
}
    public Collection<Film> getCommonFilms(int userId, int friendId) {
        final String GET_COMMON_FILMS =
                "SELECT f.*, COALESCE(l.likes_count, 0) AS likes_count " +
                        "FROM Films f " +
                        "LEFT JOIN (" +
                        "SELECT filmId, COUNT(*) AS likes_count " +
                        "FROM Likes " +
                        "GROUP BY filmId" +
                        ") l ON f.id = l.filmId " +
                        "WHERE f.id IN (" +
                        "SELECT filmId FROM Likes WHERE userId = ? " +
                        "INTERSECT " +
                        "SELECT filmId FROM Likes WHERE userId = ?" +
                        ") " +
                        "ORDER BY likes_count DESC, f.id";
        return findMany(GET_COMMON_FILMS, userId, friendId);
    }
}
