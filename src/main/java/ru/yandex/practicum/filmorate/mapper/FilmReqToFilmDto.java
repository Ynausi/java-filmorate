package ru.yandex.practicum.filmorate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Repository.RatingRepository;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmRequest;

@Component
@RequiredArgsConstructor
public class FilmReqToFilmDto {
    private final RatingRepository ratingRepository;

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
        dto.setGenres(filmRequest.getGenres());
        return dto;
    }
}

