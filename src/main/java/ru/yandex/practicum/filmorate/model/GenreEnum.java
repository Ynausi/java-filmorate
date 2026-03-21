package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GenreEnum {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    CARTOON("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION("Боевик");

    private String title;

    @JsonValue
    public String getTitle() {
        return title;
    }

    GenreEnum(String title) {
        this.title = title;
    }
}
