package ru.yandex.practicum.filmorate.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Review;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepositoryImpl extends BaseRepository<Review> implements ReviewRepository{
    private static final String FIND_ALL_REVIEWS = "SELECT * FROM Reviews";
    private static final String FIND_BY_ID = "SELECT * FROM Reviews WHERE reviewId = ?";
    private static final String ADD_REVIEW = "INSERT INTO Reviews (content,isPositive,userId,filmId,useful) " +
                                            "VALUES (?,?,?,?,?)";
    private static final String UPDATE_REVIEW = "UPDATE Reviews SET " +
            "content = ?, isPositive = ?, userId = ?, filmId = ?, useful = ? " +
            "WHERE reviewId = ?";
    private static final String DELETE_REVIEW = "DELETE FROM Reviews WHERE reviewId = ?";
    private static final String FIND_REVIEWS_FOR_FILM = "SELECT * FROM Reviews WHERE filmId = ? LIMIT ?";
    public ReviewRepositoryImpl(JdbcTemplate jdbc, RowMapper<Review> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Review> getAll() {
        return findMany(FIND_ALL_REVIEWS);
    }

    @Override
    public Optional<Review> findById(int reviewId) {
        return findOne(FIND_BY_ID,reviewId);
    }

    @Override
    public Review save(Review review) {
        int id = insertAndReturnId(ADD_REVIEW,
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful()
        );
        review.setReviewId(id);
        return review;
    }

    @Override
    public Review update(Review review) {
        update(UPDATE_REVIEW,
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful(),
                review.getReviewId()
        );
        return review;
    }

    @Override
    public boolean delete(int reviewId) {
        return delete(DELETE_REVIEW,reviewId);
    }

    @Override
    public Collection<Review> getReviewsForFilm(int filmId,int count) {
        return findMany(FIND_REVIEWS_FOR_FILM,filmId,count);
    }
}
