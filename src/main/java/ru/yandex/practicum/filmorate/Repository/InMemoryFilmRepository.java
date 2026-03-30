package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.*;

@Repository
public class InMemoryFilmRepository extends BaseRepository<Film> implements FilmRepository {
    private static final String FIND_ALL_FILMS = "SELECT * FROM Films";
    private static final String FIND_BY_ID = "SELECT * FROM Films WHERE id = ? ";
    private static final String PUT_FILM = "INSERT INTO Films(name,description,releaseDate,ratingId,duration,directorId) " +
                                        "VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_FILM = "UPDATE Films SET " +
            "name = ?, description = ?,releaseDate = ?,ratingId = ?,duration = ?,directorId = ? " +
            "WHERE id = ?";

    public InMemoryFilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Film> findAll() {
        return findMany(FIND_ALL_FILMS);
    }

    @Override
    public Optional<Film> findById(int id) {
        return findOne(FIND_BY_ID,id);
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
    public Collection<Film> getPopularFilms(int count, Integer genreId, Integer year) {
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT f.* FROM Films f " +
                        "LEFT JOIN (SELECT filmId, COUNT(*) as likes_count FROM Likes GROUP BY filmId) l ON f.id = l.filmId "
        );

        if (genreId != null) {
            sql.append("INNER JOIN Film_Genre fg ON f.id = fg.filmId ");
        }

        sql.append("WHERE 1=1 ");

        if (genreId != null) {
            sql.append("AND fg.genreId = ? ");
            params.add(genreId);
        }

        if (year != null) {
            sql.append("AND EXTRACT(YEAR FROM f.releaseDate) = ? ");
            params.add(year);
        }

        sql.append("ORDER BY COALESCE(l.likes_count, 0) DESC LIMIT ?");
        params.add(count);

       return findMany(sql.toString(), params.toArray());
    }

    @Override
    public Collection<Film> getDirectorFilmsByLikes(int directorId) {
        final String GET_DIRECTORS_FILMS_BY_LIKES =
                        "SELECT f.*,COALESCE(l.likes_count,0) AS likes_count " +
                        "FROM Films f " +
                        "LEFT JOIN(" +
                                "SELECT filmId, COUNT(*) as likes_count " +
                                "FROM Likes " +
                                "GROUP BY filmId " +
                                ") l ON f.id = l.filmId " +
                        "WHERE f.directorId = ? " +
                        "ORDER BY likes_count DESC";
        return findMany(GET_DIRECTORS_FILMS_BY_LIKES, directorId);
    }

    @Override
    public Collection<Film> getDirectorFilmsByYear(int directorId) {
        return List.of();
    }
}
