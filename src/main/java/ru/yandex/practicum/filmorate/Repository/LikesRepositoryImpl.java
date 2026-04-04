package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Likes;

@Repository
public class LikesRepositoryImpl extends BaseRepository<Likes> implements LikesRepository {
    private static final String ADD_LIKE_TO_TAB = "INSERT INTO Likes (userId,filmId)" +
            "Values (?,?)";
    private static final String DELETE_LIKE_FROM_TAB = "DELETE FROM Likes WHERE filmId = ? AND userId = ?";
    private static final String DELETE_ALL_LIKES_FOR_FILM = "DELETE FROM Likes WHERE filmId = ?";
    private static final String DELETE_ALL_LIKES_FOR_USER = "DELETE FROM Likes WHERE userId = ?";
    private static final String CHECK_LIKE_EXISTS = "SELECT COUNT(*) FROM Likes WHERE userId = ? AND filmId = ?";

    public LikesRepositoryImpl(JdbcTemplate jdbc, RowMapper<Likes> mapper) {
        super(jdbc, mapper);
    }

    public void addLikeToFilm(int userId, int filmId) {
        update(ADD_LIKE_TO_TAB,
                userId,
                filmId);
    }

    public void deleteLikeFromFilm(int filmId, int userId) {
        deleteFromTableWithDiffKey(DELETE_LIKE_FROM_TAB,
                filmId,
                userId);
    }

    @Override
    public void deleteAllLikesForFilm(int filmId) {
        jdbc.update(DELETE_ALL_LIKES_FOR_FILM, filmId);
    }

    @Override
    public void deleteAllLikesForUser(int userId) {
        jdbc.update(DELETE_ALL_LIKES_FOR_USER, userId);
    }

    @Override
    public boolean isLikeExist(int userId, int filmId) {
        Integer count = jdbc.queryForObject(CHECK_LIKE_EXISTS, Integer.class, userId, filmId);
        return count != null && count > 0;
    }
}