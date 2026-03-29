package ru.yandex.practicum.filmorate.Repository;

import ru.yandex.practicum.filmorate.model.FriendStatus;

public interface FriendshipRepository {

    public void addFriendShip(int userId, int friendId, FriendStatus friendStatus);

    public void deleteFriendShip(int userId, int friendId);
}