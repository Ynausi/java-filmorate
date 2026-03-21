package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import java.util.Optional;


@Repository
public class FilmGenreRepositoryImpl extends BaseRepository<FilmGenre> implements FilmGenreRepository {
    private static final String ADD_GENRE_TO_FILM = "INSERT INTO Film_Genre (filmId,genreId) VALUES(?,?)";
    private static final String FIND_BY_ID = "SELECT * FROM Film_Genre WHERE filmId = ?";

    public FilmGenreRepositoryImpl(JdbcTemplate jdbc, RowMapper<FilmGenre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void addGenreToFilm(int filmId, int genreId) {
        update(ADD_GENRE_TO_FILM,filmId,genreId);
    }

    @Override
    public Optional<FilmGenre> findById(int id) {
        return findOne(FIND_BY_ID,id);
    }

}
