package ru.yandex.practicum.filmorate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.MyAnnotations.Loggable;
import ru.yandex.practicum.filmorate.Repository.GenreRepository;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

@Component
@RequiredArgsConstructor
public class FilmToDto {
    private final GenreRepository genreRepository;

    @Loggable(value = "Пребразование FilmToDto",level = LogLevel.INFO)
    public FilmDto toData(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setDuration(film.getDuration());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setGenres(genreRepository.findAllGenresForFilm(film.getId()));
        dto.setRatingId(film.getRatingId());
        return dto;
    }
}
