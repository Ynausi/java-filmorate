package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.FilmRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    FilmRepository filmRepository;

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
        return null;
    }

    @Override
    public Film findById(int id) {
        return filmRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Фильма с id: " + id + "не существует"));
    }

    @Override
    public Boolean exist(int id) {
        return filmRepository.exist(id);
    }
}
