-- changeSet: id: 1 author: Slava comment: Индекс для поиска по имени студента:
DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_indexes WHERE schemaname = 'public' AND indexname = 'idx_student_name'
    ) THEN
        CREATE INDEX idx_student_name ON public.student (name);
    END IF;
END $$;

-- changeSet: id: 2 author: Slava comment: Индекс для поиска по названию и цвету факультета:
DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_indexes WHERE schemaname = 'public' AND indexname = 'idx_faculty_name_color'
    ) THEN
        CREATE INDEX idx_faculty_name_color ON public.faculty (name, color);
    END IF;
END $$;
