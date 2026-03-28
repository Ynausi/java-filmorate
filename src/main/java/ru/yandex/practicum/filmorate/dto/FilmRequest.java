package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.MyAnnotations.MinDate;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class FilmRequest {
    private Integer id;
    @NotBlank(message = "У фильма должно быть название")
    private String name;
    @Size(max = 200, message = "не больше 200 символов")
    private String description;
    @MinDate
    private LocalDate releaseDate;
    @PositiveOrZero
    private Integer duration;
    private Set<Director> directors = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();
    private Rating mpa;
}