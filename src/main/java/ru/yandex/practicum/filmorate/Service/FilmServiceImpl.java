package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.FilmRepository;
import ru.yandex.practicum.filmorate.Repository.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Override
    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film save(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film update(int id, Film film) {
        if (!filmRepository.exist(id)) {
           throw new NotFoundException("Фильма с id: " + id + "не существует");
        }
        return filmRepository.update(id,film);
    }

    @Override
    public Film findById(int id) {
        return filmRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Фильма с id: " + id + "не существует"));
    }

    @Override
    public Film addLikeToFilm(int filmId, int userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() ->
                        new NotFoundException("Фильма с id: " + filmId + "не существует"));
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("Пользователя с id: " + userId + " не существует."));
        filmRepository.addLikeToFilm(filmId,userId);
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
        filmRepository.deleteLikeFromFilm(filmId,userId);
        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }

    @Override
    public Boolean exist(int id) {
        return filmRepository.exist(id);
    }
}
