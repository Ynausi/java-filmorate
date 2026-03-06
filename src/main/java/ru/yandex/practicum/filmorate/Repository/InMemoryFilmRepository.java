package ru.yandex.practicum.filmorate.Repository;


import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryFilmRepository implements FilmRepository {

    private Map<Integer, Film> films = new HashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film save(Film film) {
        int id = atomicInteger.incrementAndGet();
        film.setId(id);
        films.put(film.getId(),film);
        return film;
    }

    @Override
    public Film update(int id,Film film) {
        Film oldFilm = films.get(id);
        oldFilm = film;
        films.replace(id,oldFilm);
        return oldFilm;
    }

    @Override
    public Film addLikeToFilm(int filmId, int userId) {
        Film film = films.get(filmId);
        film.getLikes().add(userId);
        films.put(filmId,film);
        return film;
    }

    @Override
    public Film deleteLikeFromFilm(int filmId, int userId) {
        Film film = films.get(filmId);
        film.getLikes().remove(userId);
        films.put(filmId,film);
        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        Comparator<Film> byCountOfLikes = Comparator.comparingInt(film -> film.getLikes().size());
        return films.values().stream().sorted(byCountOfLikes.reversed()).limit(count).toList();
    }

    @Override
    public Boolean exist(int id) {
        return films.containsKey(id);
    }

}
