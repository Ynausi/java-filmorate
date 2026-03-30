package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.boot.logging.LogLevel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.GenreEnum;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    @Loggable(value = "Пребразование Genre",level = LogLevel.DEBUG)
    public Genre mapRow(ResultSet resultSet,int numRow) throws SQLException {
    public Genre mapRow(ResultSet resultSet, int numRow) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("id"));
        genre.setName(GenreEnum.valueOf(resultSet.getString("name")));
        return genre;
    }
}
