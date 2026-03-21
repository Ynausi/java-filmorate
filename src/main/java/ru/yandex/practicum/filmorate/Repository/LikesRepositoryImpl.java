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

    public LikesRepositoryImpl(JdbcTemplate jdbc, RowMapper<Likes> mapper) {
        super(jdbc, mapper);
    }

    public void addLikeToFilm(int userId,int filmId) {
        update(ADD_LIKE_TO_TAB,
                userId,
                filmId);
    }

    public void deleteLikeFromFilm(int filmId,int userId) {
        deleteFromTableWithDiffKey(DELETE_LIKE_FROM_TAB,
                filmId,
                userId);
    }

}
