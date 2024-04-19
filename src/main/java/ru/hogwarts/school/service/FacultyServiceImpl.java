package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FacultyServiceImpl implements FacultyService {

    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private long facultyIdCounter = 0;

    @Override
    public Faculty create(Faculty faculty) {
        for (Faculty existingFaculty : facultyMap.values()) {
            if (existingFaculty.getName().equalsIgnoreCase(faculty.getName())) {
                throw new IllegalArgumentException("Факультет с таким именем уже существует.");
            }
        }
        Long id = ++facultyIdCounter;
        faculty.setId(id);
        facultyMap.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty read(long id) {
        return facultyMap.get(id);
    }

    @Override
    public Faculty update(Faculty faculty) {
        Long id = faculty.getId();
        if (!facultyMap.containsKey(id)) {
            throw new IllegalArgumentException("Факультет с id " + id + " не найден.");
        }
        facultyMap.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty delete(long id) {
        return facultyMap.remove(id);
    }
    public List<Faculty> filterFacultiesByColor(String color) {
        List<Faculty> filteredFaculties = new ArrayList<>();
        for (Faculty faculty : facultyMap.values()) {
            if (faculty.getColor().equalsIgnoreCase(color)) {
                filteredFaculties.add(faculty);
            }
        }
        return filteredFaculties;
    }


}
