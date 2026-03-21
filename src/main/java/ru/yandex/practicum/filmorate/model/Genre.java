package ru.yandex.practicum.filmorate.model;

public enum Genre {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    CARTOON("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION("Боевик");

    private String title;
    public String getTitle() {
        return title;
    }

    Genre(String title) {
        this.title = title;
    }

}
