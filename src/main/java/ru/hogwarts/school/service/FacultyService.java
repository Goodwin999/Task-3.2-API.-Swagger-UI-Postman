package ru.hogwarts.school.service;
import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Faculty;
import java.util.List;

public interface FacultyService {
    Faculty create(Faculty faculty) throws DatabaseAccessException;
    Faculty read(long id);
    Faculty update(Faculty faculty);
    boolean delete(long id);
    List<Faculty> filterFacultiesByColor(String color);
    List<Faculty> searchFacultiesByNameOrColorIgnoreCase(String searchString);
}
