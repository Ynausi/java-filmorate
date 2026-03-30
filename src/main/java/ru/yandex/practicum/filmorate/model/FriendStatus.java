package ru.yandex.practicum.filmorate.model;

public enum FriendStatus {
    UNCONFIRMED("Неподтвержденный"),
    CONFIRMED("Подтвержденный");

    private String title;

    FriendStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}