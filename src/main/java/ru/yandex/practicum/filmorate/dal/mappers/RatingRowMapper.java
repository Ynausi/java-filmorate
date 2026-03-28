package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.boot.logging.LogLevel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.RatingEnum;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RatingRowMapper implements RowMapper<Rating> {
    @Override
    @Loggable(value = "Пребразование Rating",level = LogLevel.DEBUG)
    public Rating mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Rating rating = new Rating();
        rating.setId(resultSet.getInt("id"));
        rating.setName(RatingEnum.fromTitle(resultSet.getString("name")));
        return rating;
    }
}
