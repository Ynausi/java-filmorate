package ru.yandex.practicum.filmorate.mapper;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class FilmDtoToData {
    @Loggable(value = "Пребразование FilmDtoToData",level = LogLevel.DEBUG)
    public Film toData(FilmDto dto) {
        Film film = new Film();
        if (dto.getId() != null) {
            film.setId(dto.getId());
        }
        film.setName(dto.getName());
        film.setDescription(dto.getDescription());
        film.setReleaseDate(dto.getReleaseDate());
        film.setDuration(dto.getDuration());
        film.setDirectorId(dto.getDirectorId());
        film.setRatingId(dto.getRatingId());
        return film;
    }
}