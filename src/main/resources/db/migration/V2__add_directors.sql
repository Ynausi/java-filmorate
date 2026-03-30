-- Создаём таблицу режиссёров
CREATE TABLE IF NOT EXISTS Directors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Добавляем столбец directorId в таблицу Films
ALTER TABLE Films ADD COLUMN directorId INT;

-- Добавляем внешний ключ
ALTER TABLE Films
    ADD CONSTRAINT fk_films_director
    FOREIGN KEY (directorId) REFERENCES Directors(id);
