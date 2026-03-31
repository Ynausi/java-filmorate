package ru.yandex.practicum.filmorate.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Repository.DirectorRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorImpl implements DirectorService{
    private final DirectorRepository directorRepository;
    @Override
    public Collection<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    public Director findById(int directorId) {
        return directorRepository.findById(directorId).orElseThrow(() ->
                new NotFoundException("No director with id = " + directorId));
    }

    @Override
    public Director save(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Director update(Director director) {
        if (directorRepository.findById(director.getId()).isEmpty()) {
            throw new NotFoundException("No director with id: " + director.getId());
        }
        return directorRepository.update(director);
    }

    @Override
    public boolean delete(int directorId) {
        if (directorRepository.findById(directorId).isEmpty()) {
            throw new NotFoundException("No director with id: " + directorId);
        }
        return directorRepository.delete(directorId);
    }
}