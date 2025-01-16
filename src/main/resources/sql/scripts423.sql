-- Получить имена и возраст студентов, а также названия их факультетов
SELECT s.name AS student_name, s.age AS student_age, f.name AS faculty_name
FROM Student s
INNER JOIN Faculty f ON s.faculty_id = f.id;

-- Получить студентов с аватарками

SELECT s.name AS student_name, s.age AS student_age, a.file_path AS avatar_path
FROM Students s
JOIN Avatars a ON s.id = a.student_id
WHERE a.file_path IS NOT NULL;

