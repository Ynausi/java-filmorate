package ru.yandex.practicum.filmorate.Service;


import ru.yandex.practicum.filmorate.model.Rating;
import java.util.Collection;

public interface MpaService {

    Collection<Rating> findAllRatings();

    Rating findById(int id);


}
