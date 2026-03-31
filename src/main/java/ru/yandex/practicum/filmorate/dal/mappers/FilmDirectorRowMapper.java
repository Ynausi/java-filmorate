package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.boot.logging.LogLevel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.model.FilmDirector;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class FilmDirectorRowMapper implements RowMapper<FilmDirector> {
    @Override
    @Loggable(value = "Пребразование FilmDirector", level = LogLevel.DEBUG)
    public FilmDirector mapRow(ResultSet resultSet, int numRow) throws SQLException {
        FilmDirector filmDirector = new FilmDirector();
        filmDirector.setFilmId(resultSet.getInt("filmId"));
        filmDirector.setDirectorId(resultSet.getInt("directorId"));
        return filmDirector;
    }
}