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

    @Loggable(value = "Пребразование FilmReqToFilmDto",level = LogLevel.DEBUG)
    public FilmDto toDto(FilmRequest filmRequest) {
        FilmDto dto = new FilmDto();
        dto.setName(filmRequest.getName());
        dto.setDescription(filmRequest.getDescription());
        dto.setReleaseDate(filmRequest.getReleaseDate());
        dto.setDuration(filmRequest.getDuration());
        if (filmRequest.getMpa() != null && filmRequest.getMpa().getId() != null) {
            dto.setRatingId(filmRequest.getMpa().getId());
            dto.setMpa(filmRequest.getMpa());
        } else {
            dto.setRatingId(null);
            dto.setMpa(null);
        }
        dto.setDirectors(filmRequest.getDirectors());
        dto.setGenres(filmRequest.getGenres());
        return dto;
    }
}