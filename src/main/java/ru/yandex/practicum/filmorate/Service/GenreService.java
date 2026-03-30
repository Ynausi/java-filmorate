package ru.yandex.practicum.filmorate.Service;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreService {

    Collection<Genre> findAllFilmsWithGenre();

    Genre findById(int id);
}