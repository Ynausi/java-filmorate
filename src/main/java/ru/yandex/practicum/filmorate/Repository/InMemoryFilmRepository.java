package ru.yandex.practicum.filmorate.Repository;


import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryFilmRepository implements FilmRepository{

    private Map<Integer, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAll(){
        return films.values();
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film save(Film film) {
        film.setId(getNextId());
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
    public Boolean exist(int id) {
        return films.containsKey(id);
    }



    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
