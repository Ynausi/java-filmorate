package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.boot.logging.LogLevel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class FilmGenreRowMapper implements RowMapper<FilmGenre> {
    @Override
    @Loggable(value = "Пребразование FilmGenre", level = LogLevel.DEBUG)
    public FilmGenre mapRow(ResultSet resultSet, int numRow) throws SQLException {
        FilmGenre filmGenre = new FilmGenre();
        filmGenre.setFilmId(resultSet.getInt("filmId"));
        filmGenre.setGenreId(resultSet.getInt("genreId"));
        return filmGenre;
    }
}