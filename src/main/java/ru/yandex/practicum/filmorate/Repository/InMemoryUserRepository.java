package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.User;
import java.util.*;

@Repository
public class InMemoryUserRepository extends BaseRepository<User> implements UserRepository {
    private static final String FIND_ALL_USERS = "SELECT * FROM Users";
    private static final String FIND_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String PUT_USER = "INSERT INTO Users(email, login, name, birthday) " +
             "Values (?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE Users SET " +
            "email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
    private static final String FIND_USER_FRIENDS = "SELECT u.* " +
            "    FROM Users u " +
            "    JOIN Friendship f ON u.id = f.friendId " +
            "    WHERE f.userId = ? AND f.status = ?";
    private static final String FIND_COMMON_FRIENDS = "SELECT * " +
            "FROM Users " +
            "WHERE id IN ( " +
            "SELECT friendId " +
            "FROM Friendship " +
            "WHERE userId = ? " +
            "INTERSECT " +
            "SELECT friendId " +
            "FROM Friendship " +
            "WHERE userId = ? " +
            ")";

    public InMemoryUserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<User> findAll() {
        return findMany(FIND_ALL_USERS);
    }

    @Override
    public Optional<User> findById(int id) {
        return findOne(FIND_BY_ID,id);
    }

    @Override
    public User save(User user) {
        int id = insertAndReturnId(PUT_USER,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        update(UPDATE_USER,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    @Override
    public Collection<User> getUserFriends(int userId) {
        return findMany(FIND_USER_FRIENDS,userId, FriendStatus.CONFIRMED.name());
    }

    @Override
    public Collection<User> getCommonFriends(int userId,int friendId) {
        return findMany(FIND_COMMON_FRIENDS,userId,friendId);
    }

}
