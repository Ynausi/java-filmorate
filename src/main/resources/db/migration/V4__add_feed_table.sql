-- Таблица для хранения событий
CREATE TABLE IF NOT EXISTS Event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,           -- пользователь, который совершил действие
    event_type VARCHAR(20) NOT NULL, -- LIKE, REVIEW, FRIEND
    operation VARCHAR(20) NOT NULL,  -- ADD, REMOVE, UPDATE
    entity_id INT NOT NULL,          -- ID сущности (фильма, пользователя, отзыва)
    timestamp BIGINT NOT NULL,       -- время события в миллисекундах
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Индекс для быстрого получения событий пользователя
CREATE INDEX idx_event_user_id ON Event(user_id);
CREATE INDEX idx_event_timestamp ON Event(timestamp);