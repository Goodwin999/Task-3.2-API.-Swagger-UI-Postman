package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

public class FacultyServiceImpl implements FacultyService {

    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private long facultyIdCounter = 0;

    @Override
    public Faculty create(Faculty faculty) {

        return faculty;
    }

    @Override
    public Faculty read(long id) {

        return null;
    }

    @Override
    public Faculty update(Faculty faculty) {

        return null;
    }

    @Override
    public Faculty delete(long id) {

        return null;
    }
}
