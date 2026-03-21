package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.GenreRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.Collection;


@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Collection<Genre> findAllFilmsWithGenre() {
        return genreRepository.findAllGenres();
    }

    @Override
    public Genre findById(int id) {
        return genreRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Рейтинга с id " + id + " нет"));
    }
}
