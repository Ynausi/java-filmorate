package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.RatingRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final RatingRepository ratingRepository;

    @Override
    public Collection<Rating> findAllRatings() {
        return ratingRepository.findAllRating().stream().collect(Collectors.toSet());
    }

    @Override
    public Rating findById(int id) {
        return ratingRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Рейтинга с id " + id + " нет"));
    }
}