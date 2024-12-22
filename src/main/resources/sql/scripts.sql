-- Получить всех студентов
SELECT * FROM students;

-- Получить студентов в возрасте от 10 до 20 лет
SELECT * FROM students
WHERE age BETWEEN 10 AND 20;

-- Получить имена всех студентов
SELECT name FROM students;

/* Получить всех студентов,
у которых в имени есть буква "О" */
SELECT * FROM students
WHERE name LIKE '%О%';

-- Получить всех студентов, у которых возраст меньше 15
SELECT * FROM students
WHERE age < 16;

-- Получить всех студентов, отсортированных по возрасту
SELECT * FROM students
ORDER BY age;
