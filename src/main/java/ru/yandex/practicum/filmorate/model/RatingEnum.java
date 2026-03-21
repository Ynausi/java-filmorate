package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RatingEnum {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private String title;

    @JsonValue
    public String getTitle() {
        return title;
    }

    public static RatingEnum fromTitle(String title) {
        for (RatingEnum r:RatingEnum.values()) {
            if (r.getTitle().equals(title)) {
                return r;
            }
        }
        throw new IllegalArgumentException("No enum with this title " + title);
    }

    RatingEnum(String title) {
        this.title = title;
    }


}
