package ru.yandex.practicum.filmorate.Service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmRequest;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final FilmGenreRepository filmGenreRepository;
    private final GenreRepository genreRepository;
    private final FilmReqToFilmDto filmReqToFilmDto;
    private final FilmDtoToData filmDtoToData;
    private final FilmDtoToResp filmDtoToResp;
    private final FilmToDto filmToDto;
    private final RatingRepository ratingRepository;
    private final DirectorRepository directorRepository;
    private final FilmDirectorsRepository filmDirectorsRepository;

    @Override
    public Collection<FilmResponse> findAll() {
        return filmRepository.findAll().stream()
                .map(this::buildFilmResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FilmResponse save(FilmRequest film) {
        FilmDto dto = filmReqToFilmDto.toDto(film);
        if (dto.getMpa() != null && dto.getRatingId() != null) {
            dto.setRatingId(film.getMpa().getId());
            dto.setMpa(ratingRepository.findById(dto.getMpa().getId()).orElseThrow(() ->
                    new NotFoundException("No such Elem mpa")));
        } else {
            dto.setRatingId(null);
            dto.setMpa(null);
        }
        Film forSave = filmDtoToData.toData(dto);
        Film saved = filmRepository.save(forSave);
        dto.setId(saved.getId());
        if (dto.getGenres() != null && !dto.getGenres().isEmpty()) {
            for (Genre genre : dto.getGenres()) {
                if (genreRepository.findById(genre.getId()).isEmpty()) {
                    throw new NotFoundException("No such genre");
                }
                filmGenreRepository.addGenreToFilm(dto.getId(),genre.getId());
            }
            dto.setGenres(genreRepository.findAllGenresForFilm(dto.getId()));
            System.out.println(dto.getGenres());
        } else {
            dto.setGenres(Collections.emptySet());
        }
        if (dto.getDirectors() != null && !dto.getDirectors().isEmpty()) {
            for (Director director : dto.getDirectors()) {
                if (directorRepository.findById(director.getId()).isEmpty()) {
                    throw new NotFoundException("No such director");
                }
                filmDirectorsRepository.addDirectorToFilm(dto.getId(),director.getId());
            }
            dto.setDirectors(directorRepository.findAllDirectorsForFilm(dto.getId()));
            System.out.println(dto.getDirectors());
        } else {
            dto.setDirectors(Collections.emptySet());
        }
        return filmDtoToResp.toResp(dto);
    }

    @Override
    public FilmResponse update(FilmRequest film) {
        if (film.getId() == null) {
            throw new ValidationException("id must be for update");
        }
        if (filmRepository.findById(film.getId()).isEmpty()) {
           throw new NotFoundException("Фильма с id: " + film.getId() + "не существует");
        }
        FilmDto dto = filmReqToFilmDto.toDto(film);
        dto.setId(film.getId());
        updateDirectors(dto.getId(),dto.getDirectors());
        Film forUpdate = filmDtoToData.toData(dto);
        Film update = filmRepository.update(forUpdate);
        return buildFilmResponse(forUpdate);
    }

    @Override
    public FilmResponse findById(int id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Фильма с id: " + id + "не существует"));
        return buildFilmResponse(film);
    }

    @Override
    public Film addLikeToFilm(int filmId, int userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() ->
                        new NotFoundException("Фильма с id: " + filmId + "не существует"));
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + userId + " не существует."));
        likesRepository.addLikeToFilm(userId,filmId);
        return film;
    }

    @Override
    public Film deleteLikeFromFilm(int filmId, int userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() ->
                        new NotFoundException("Фильма с id: " + filmId + "не существует"));
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + userId + " не существует."));
        likesRepository.deleteLikeFromFilm(filmId,userId);
        return film;
    }

    @Override
    public Collection<FilmResponse> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count)
                .stream()
                .map(this::buildFilmResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<FilmResponse> getDirectorFilmsByLikesOrYear(int directorId, String sortBy) {
        if (sortBy.equals("likes")) {
            return filmRepository.getDirectorFilmsByLikes(directorId).stream()
                .map(this::buildFilmResponse)
                .collect(Collectors.toList());
        } else if (sortBy.equals("year")) {
            return filmRepository.getDirectorFilmsByYear(directorId).stream()
                    .map(this::buildFilmResponse)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("sortBy должно быть либо year либо like");
        }
    }

    //Преобразование из Film в FilmResponse чтобы убрать повторения
    private FilmResponse buildFilmResponse(Film film) {
        FilmDto dto = filmToDto.toData(film);
        dto.setMpa(
                dto.getRatingId() != null
                        ? ratingRepository.findById(dto.getRatingId()).orElse(null)
                        : null
        );

        if (dto.getId() != null) {
            dto.setDirectors(directorRepository.findAllDirectorsForFilm(dto.getId()));
        }

        if (dto.getId() != null) {
            dto.setGenres(genreRepository.findAllGenresForFilm(dto.getId()));
        }
        return filmDtoToResp.toResp(dto);
    }

    private void updateDirectors(int filmdId, Set<Director> directors) {
        filmDirectorsRepository.deleteDirectorsFromFilm(filmdId);
        if (directors == null || directors.isEmpty()) {
            return;
        }
        for (Director director : directors) {
            if (directorRepository.findById(director.getId()).isEmpty()) {
                throw new NotFoundException("No such director");
            }
            filmDirectorsRepository.addDirectorToFilm(filmdId,director.getId());
        }
    }
}
