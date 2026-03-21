package ru.yandex.practicum.filmorate.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import java.util.Collections;

@Component
public class FilmDtoToResp {
    public FilmResponse toResp(FilmDto dto) {
        FilmResponse filmResponse = new FilmResponse();
        filmResponse.setId(dto.getId());
        filmResponse.setName(dto.getName());
        filmResponse.setDescription(dto.getDescription());
        filmResponse.setReleaseDate(dto.getReleaseDate());
        filmResponse.setDuration(dto.getDuration());
        if (dto.getGenres() == null || dto.getGenres().isEmpty()) {
            filmResponse.setGenres(Collections.emptySet());
        } else {
            filmResponse.setGenres(dto.getGenres());
        }
        filmResponse.setMpa(dto.getMpa());
        return filmResponse;
    }
}
