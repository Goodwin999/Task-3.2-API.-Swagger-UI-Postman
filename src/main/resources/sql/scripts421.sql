--Шаг 1: Ограничение на возраст студента
ALTER TABLE StudentADD CONSTRAINT check_student_age CHECK (age >= 16);

-- Шаг 2: Уникальные и ненулевые имена студентов
ALTER TABLE StudentALTER COLUMN name SET NOT NULL;
ALTER TABLE Student ADD CONSTRAINT unique_student_name UNIQUE (name);

-- Шаг 3: Уникальная пара "название - цвет" для факультетов
ALTER TABLE FacultyADD CONSTRAINT unique_faculty_name_color UNIQUE (name, color);

-- Шаг 4: Возраст по умолчанию для студентов
ALTER TABLE StudentALTER COLUMN age SET DEFAULT 20;