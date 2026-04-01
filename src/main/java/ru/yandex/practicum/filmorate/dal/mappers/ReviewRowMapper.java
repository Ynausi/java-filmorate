package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("reviewId"));
        review.setContent(resultSet.getString("content"));
        review.setIsPositive(resultSet.getBoolean("isPositive"));
        review.setUserId(resultSet.getInt("userId"));
        review.setFilmId(resultSet.getInt("filmId"));
        review.setUseful(resultSet.getInt("useful"));
        return review;
    }
}