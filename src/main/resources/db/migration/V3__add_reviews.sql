CREATE TABLE IF NOT EXISTS Reviews (
 reviewId INT AUTO_INCREMENT PRIMARY KEY,
 content VARCHAR(100) NOT NULL,
 isPositive BOOLEAN NOT NULL,
 userId INT NOT NULL,
 filmId INT NOT NULL,
 useful INT NOT NULL DEFAULT 0,
 CONSTRAINT fk_reviews_user FOREIGN KEY (userId) REFERENCES Users(id),
 CONSTRAINT fk_reviews_film FOREIGN KEY (filmId) REFERENCES Films(id)
);
CREATE TABLE IF NOT EXISTS Review_Likes(
 reviewId INT NOT NULL,
 userId INT NOT NULL,
 useful INT NOT NULL,
 PRIMARY KEY(reviewId,userId),
 CONSTRAINT fk_reviewLike_review FOREIGN KEY (reviewId) REFERENCES Reviews(reviewId),
 CONSTRAINT fk_reviewLike_user FOREIGN KEY (userId) REFERENCES Users(id)
)