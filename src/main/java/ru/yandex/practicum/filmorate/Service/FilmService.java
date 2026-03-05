package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {

    Collection<Film> findAll();

    Film save(Film film);

    Film update(int id,Film film);

    Film findById(int id);

    Boolean exist(int id);
}
