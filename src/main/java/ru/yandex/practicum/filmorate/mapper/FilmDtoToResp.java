package ru.yandex.practicum.filmorate.mapper;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import java.util.Collections;

@Component
public class FilmDtoToResp {
    @Loggable(value = "Пребразование FilmDtoToResp",level = LogLevel.INFO)
    public FilmResponse toResp(FilmDto dto) {
        FilmResponse filmResponse = new FilmResponse();
        filmResponse.setId(dto.getId());
        filmResponse.setName(dto.getName());
        filmResponse.setDescription(dto.getDescription());
        filmResponse.setReleaseDate(dto.getReleaseDate());
        filmResponse.setDuration(dto.getDuration());
        filmResponse.setDirector(dto.getDirector());
        if (dto.getGenres() == null || dto.getGenres().isEmpty()) {
            filmResponse.setGenres(Collections.emptySet());
        } else {
            filmResponse.setGenres(dto.getGenres());
        }
        filmResponse.setMpa(dto.getMpa());
        return filmResponse;
    }
}
