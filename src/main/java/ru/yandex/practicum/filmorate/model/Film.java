package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.MyAnnotations.MinDate;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "У фильма должно быть название")
    private String name;
    @Size(max = 200,message = "не больше 200 символов")
    private String description;
    @MinDate
    private LocalDate releaseDate;
    @PositiveOrZero
    private int duration;
}
