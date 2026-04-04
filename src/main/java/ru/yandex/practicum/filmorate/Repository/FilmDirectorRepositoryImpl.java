package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmDirector;

import java.util.Optional;

@Repository
public class FilmDirectorRepositoryImpl extends BaseRepository<FilmDirector> implements FilmDirectorsRepository {
    private static final String ADD_DIRECTOR_TO_FILM = "INSERT INTO Film_Directors (filmId,directorId) VALUES(?,?)";
    private static final String FIND_BY_ID = "SELECT * FROM Film_Directors WHERE filmId = ?";
    private static final String DELETE_DIRECTORS_AT_FILM = "DELETE FROM Film_Directors WHERE filmId = ?";

    public FilmDirectorRepositoryImpl(JdbcTemplate jdbc, RowMapper<FilmDirector> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void addDirectorToFilm(int filmId, int directorId) {
        update(ADD_DIRECTOR_TO_FILM, filmId, directorId);
    }

    @Override
    public Optional<FilmDirector> findById(int id) {
        return findOne(FIND_BY_ID, id);
    }

    @Override
    public void deleteDirectorsFromFilm(int filmId) {
        delete(DELETE_DIRECTORS_AT_FILM, filmId);
    }
}