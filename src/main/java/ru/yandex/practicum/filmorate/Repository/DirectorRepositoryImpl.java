package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.*;

@Repository
public class DirectorRepositoryImpl extends BaseRepository implements DirectorRepository{
    private static final String FIND_ALL_DIRECTORS = "SELECT * FROM Directors";
    private static final String FIND_BY_ID = "SELECT * FROM Directors WHERE id = ?";
    private static final String ADD_DIRECTOR = "INSERT INTO Directors(name) " +
                                                "VALUES(?)";
    private static final String UPDATE_DIRECTOR = "UPDATE Directors SET " +
                                                    "name = ? WHERE id = ?";
    private static final String DELETE_DIRECTOR = "DELETE FROM Directors WHERE id = ?";
    private static final String FIND_DIRECTORS_FOR_FILM = "SELECT d.id, d.name " +
                                                          "FROM Directors AS d " +
                                                          "JOIN Film_Directors as fd ON d.id = fd.directorId " +
                                                          "WHERE fd.filmId = ? " +
                                                          "ORDER BY d.id";

    public DirectorRepositoryImpl(JdbcTemplate jdbc, RowMapper<Director> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Set<Director> findAll() {
        return new LinkedHashSet<>(findMany(FIND_ALL_DIRECTORS));
    }

    @Override
    public Set<Director> findAllDirectorsForFilm(int filmId) {
        return new LinkedHashSet<>(findMany(FIND_DIRECTORS_FOR_FILM,filmId));
    }

    @Override
    public Optional<Director> findById(int directorId) {
        return findOne(FIND_BY_ID
                ,directorId
        );
    }

    @Override
    public Director save(Director director) {
        Integer id = insertAndReturnId(
                ADD_DIRECTOR,
                director.getName()
        );
        director.setId(id);
        return director;
    }

    @Override
    public Director update(Director director) {
        update(UPDATE_DIRECTOR,
                director.getName(),
                director.getId()
        );
        return director;
    }

    @Override
    public boolean delete(int directorId) {
        return delete(DELETE_DIRECTOR,
                directorId
        );
    }
}
