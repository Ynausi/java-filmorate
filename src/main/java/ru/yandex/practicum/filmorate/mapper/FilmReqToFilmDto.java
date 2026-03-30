package ru.yandex.practicum.filmorate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Repository.RatingRepository;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmRequest;

@Component
@RequiredArgsConstructor
public class FilmReqToFilmDto {
    private final RatingRepository ratingRepository;

    @Loggable(value = "Пребразование FilmReqToFilmDto",level = LogLevel.INFO)
    public FilmDto toDto(FilmRequest filmRequest) {
        FilmDto dto = new FilmDto();
        dto.setName(filmRequest.getName());
        dto.setDescription(filmRequest.getDescription());
        dto.setReleaseDate(filmRequest.getReleaseDate());
        dto.setDuration(filmRequest.getDuration());
        if (filmRequest.getDirector() != null && filmRequest.getDirector().getId() != null) {
            dto.setDirectorId(filmRequest.getDirector().getId());
            dto.setDirector(filmRequest.getDirector());
        } else {
            dto.setDirector(null);
            dto.setDirector(null);
        }
        if (filmRequest.getMpa() != null && filmRequest.getMpa().getId() != null) {
            dto.setRatingId(filmRequest.getMpa().getId());
            dto.setMpa(filmRequest.getMpa());
        } else {
            dto.setRatingId(null);
            dto.setMpa(null);
        }
        dto.setGenres(filmRequest.getGenres());
        return dto;
    }
}

