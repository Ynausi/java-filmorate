CREATE TABLE IF NOT EXISTS Directors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS Film_Directors (
    filmId INT NOT NULL,
    directorId INT NOT NULL,
    PRIMARY KEY (filmId, directorId),
    FOREIGN KEY (filmId) REFERENCES Films(id),
    FOREIGN KEY (directorId) REFERENCES Directors(id)
);