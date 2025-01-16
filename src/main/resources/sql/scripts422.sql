-- Шаг 1: Создание таблицы для людей (People)
CREATE TABLE People (
    id SERIAL PRIMARY KEY,        -- Уникальный идентификатор (первичный ключ)
    name VARCHAR(255) NOT NULL,    -- Имя человека (не может быть NULL)
    age INT CHECK (age >= 18),     -- Возраст (проверка, чтобы возраст был >= 18)
    has_driving_license BOOLEAN   -- Признак наличия прав
);

--Шаг 2: Создание таблицы для машин (Cars)
CREATE TABLE Cars (
    id SERIAL PRIMARY KEY,        -- Уникальный идентификатор (первичный ключ)
    brand VARCHAR(255) NOT NULL,   -- Марка машины (не может быть NULL)
    model VARCHAR(255) NOT NULL,   -- Модель машины (не может быть NULL)
    price DECIMAL(10, 2)           -- Стоимость машины (десятичный тип с точностью до 2 знаков после запятой)
);
-- Шаг 3: Создание таблицы для связи между людьми и машинами (People_Cars)
CREATE TABLE People_Cars (
    person_id INT REFERENCES People(id) ON DELETE CASCADE, -- Внешний ключ на таблицу People (удаляется вместе с человеком)
    car_id INT REFERENCES Cars(id) ON DELETE CASCADE,     -- Внешний ключ на таблицу Cars (удаляется вместе с машиной)
    PRIMARY KEY (person_id, car_id)                       -- Композитный первичный ключ (человек + машина)
);

