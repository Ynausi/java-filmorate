package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.Friendship;

@Repository
public class FriendshipRepositoryImpl extends BaseRepository<Friendship> implements FriendshipRepository {
    private static final String PUT_TO_TAB = "INSERT INTO Friendship (userId,friendId,status) " +
            "VALUES(?,?,?)";
    private static final String DELETE_FROM_TAB = "DELETE FROM Friendship " +
            "WHERE userId = ? AND friendId = ?";
    private static final String DELETE_ALL_FRIENDSHIPS_FOR_USER =
            "DELETE FROM Friendship WHERE userId = ? OR friendId = ?";

    public FriendshipRepositoryImpl(JdbcTemplate jdbc, RowMapper<Friendship> mapper) {
        super(jdbc, mapper);
    }

    public void addFriendShip(int userId, int friendId, FriendStatus friendStatus) {
        update(PUT_TO_TAB,
                userId,
                friendId,
                friendStatus.name());
    }

    public void deleteFriendShip(int userId, int friendId) {
        deleteFromTableWithDiffKey(DELETE_FROM_TAB,
                userId,
                friendId);
    }

    @Override
    public void deleteAllFriendshipsForUser(int userId) {
        jdbc.update(DELETE_ALL_FRIENDSHIPS_FOR_USER, userId, userId);
    }
}