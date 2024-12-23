liquibase formatted sql

//Индекс для поиска по имени студента
- changeSet:
      id: 2
      author: Ilone Musk
      comment: Add index on student name
      sql: |
          CREATE INDEX idx_student_name ON public.student (name);

//Индекс для поиска по названию и цвету факультета
- changeSet:
      id: 3
      author: Ilone Musk
      comment: Add index on faculty name and color
      sql: |
          CREATE INDEX idx_faculty_name_color ON public.faculty (name, color);
