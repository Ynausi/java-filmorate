package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
public class InMemoryFilmRepository extends BaseRepository<Film> implements FilmRepository {
    private static final String FIND_ALL_FILMS = "SELECT * FROM Films";
    private static final String FIND_BY_ID = "SELECT * FROM Films WHERE id = ? ";
    private static final String PUT_FILM = "INSERT INTO Films(name,description,releaseDate,ratingId,duration) " +
            "VALUES(?,?,?,?,?)";
    private static final String UPDATE_FILM = "UPDATE Films SET " +
            "name = ?, description = ?,releaseDate = ?,ratingId = ?,duration = ? " +
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
                film.getDuration()
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
                film.getId()
        );
        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return getPopularFilms(count, null, null);
    }

    @Override
    public Collection<Film> getPopularFilms(int count, Integer genreId, Integer year) {
        final String sql =
                "SELECT f.* FROM Films f " +
                        "LEFT JOIN (SELECT filmId, COUNT(*) as likes_count FROM Likes GROUP BY filmId) l ON f.id = l.filmId " +
                        "LEFT JOIN Film_Genre fg ON f.id = fg.filmId " +
                        "WHERE (? IS NULL OR fg.genreId = ?) " +
                        "AND (? IS NULL OR EXTRACT(YEAR FROM f.releaseDate) = ?) " +
                        "GROUP BY f.id " +
                        "ORDER BY COALESCE(l.likes_count, 0) DESC " +
                        "LIMIT ?";
        return findMany(sql, genreId, genreId, year, year, count);
    }

    @Override
    public Collection<Film> searchByTitle(String query) {
        String sql = "SELECT f.* FROM Films f " +
                "LEFT JOIN (SELECT filmId, COUNT(*) as likes FROM Likes GROUP BY filmId) l ON f.id = l.filmId " +
                "WHERE LOWER(f.name) LIKE LOWER(?) " +
                "GROUP BY f.id " +
                "ORDER BY COALESCE(l.likes, 0) DESC";
        return findMany(sql, "%" + query + "%");
    }

    @Override
    public Collection<Film> searchByDirector(String query) {
        String sql = "SELECT f.* FROM Films f " +
                "LEFT JOIN (SELECT filmId, COUNT(*) as likes FROM Likes GROUP BY filmId) l ON f.id = l.filmId " +
                "JOIN Film_Directors fd ON f.id = fd.filmId " +
                "JOIN Directors d ON fd.directorId = d.id " +
                "WHERE LOWER(d.name) LIKE LOWER(?) " +
                "GROUP BY f.id " +
                "ORDER BY COALESCE(l.likes, 0) DESC";
        return findMany(sql, "%" + query + "%");
    }

    @Override
    public Collection<Film> searchByTitleAndDirector(String query) {
        String sql = "SELECT f.* FROM Films f " +
                "LEFT JOIN (SELECT filmId, COUNT(*) as likes FROM Likes GROUP BY filmId) l ON f.id = l.filmId " +
                "LEFT JOIN Film_Directors fd ON f.id = fd.filmId " +
                "LEFT JOIN Directors d ON fd.directorId = d.id " +
                "WHERE LOWER(f.name) LIKE LOWER(?) OR LOWER(d.name) LIKE LOWER(?) " +
                "GROUP BY f.id " +
                "ORDER BY COALESCE(l.likes, 0) DESC";
        String pattern = "%" + query + "%";
        return findMany(sql, pattern, pattern);
    }

    @Override
    public Collection<Film> getDirectorFilmsByLikes(int directorId) {
        final String GET_DIRECTORS_FILMS_BY_LIKES =
                "SELECT f.*, COALESCE(l.likes_count,0) AS likes_count " +
                        "FROM Films f " +
                        "JOIN Film_Directors fd ON f.id = fd.filmId " +
                        "LEFT JOIN ( " +
                        "SELECT filmId, COUNT(*) as likes_count " +
                        "FROM Likes " +
                        "GROUP BY filmId " +
                        ") l ON f.id = l.filmId " +
                        "WHERE fd.directorId = ? " +
                        "ORDER BY likes_count DESC";
        return findMany(GET_DIRECTORS_FILMS_BY_LIKES, directorId);
    }

    @Override
    public Collection<Film> getDirectorFilmsByYear(int directorId) {
        final String GET_DIRECTORS_FILMS_BY_YEAR =
                "SELECT f.* " +
                        "FROM Films f " +
                        "JOIN Film_Directors fd ON f.id = fd.filmId " +
                        "WHERE fd.directorId = ? " +
                        "ORDER BY EXTRACT(YEAR FROM f.releaseDate) ASC";
        return findMany(GET_DIRECTORS_FILMS_BY_YEAR, directorId);
    }

    @Override
    public boolean delete(int filmId) {
        return delete(DELETE_FILM, filmId);
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

    @Override
    public Collection<Film> getRecommendations(int userId) {
        final String GET_RECOMMENDATIONS =
                "SELECT f.* " +
                        "FROM Films f " +
                        "JOIN Likes l ON f.id = l.filmId " +
                        "WHERE l.userId = (" +
                        "SELECT l2.userId " +
                        "FROM Likes l1 " +
                        "JOIN Likes l2 ON l1.filmId = l2.filmId " +
                        "WHERE l1.userId = ? AND l2.userId <> ? " +
                        "GROUP BY l2.userId " +
                        "ORDER BY COUNT(*) DESC, l2.userId " +
                        "LIMIT 1" +
                        ") " +
                        "AND f.id NOT IN (" +
                        "SELECT filmId FROM Likes WHERE userId = ?" +
                        ") " +
                        "ORDER BY f.id";
        return findMany(GET_RECOMMENDATIONS, userId, userId, userId);
    }
}