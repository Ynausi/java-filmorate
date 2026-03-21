package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Likes;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LikesRowMapper implements RowMapper<Likes> {
    @Override
    public Likes mapRow(ResultSet resultSet,int numRow) throws SQLException {
        Likes likes = new Likes();
        likes.setUserId(resultSet.getInt("userId"));
        likes.setFilmId(resultSet.getInt("filmId"));
        return likes;
    }
}
