package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.boot.logging.LogLevel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.model.Director;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DirectorRowMapper implements RowMapper<Director> {
    @Override
    @Loggable(value = "Пребразование Director",level = LogLevel.DEBUG)
    public Director mapRow(ResultSet resultSets, int rowNum) throws SQLException {
        Director director = new Director();
        director.setId(resultSets.getInt("id"));
        director.setName(resultSets.getString("name"));
        return director;
    }
}
