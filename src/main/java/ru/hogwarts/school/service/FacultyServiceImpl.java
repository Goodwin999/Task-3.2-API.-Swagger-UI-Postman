package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;


    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) throws DatabaseAccessException {
        try {
            faculty.setId(null);
            return facultyRepository.save(faculty);
        } catch (Exception ex) {
            throw new DatabaseAccessException("Ошибка доступа к базе данных при создании факультета: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Faculty read(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public boolean delete(long id) {
        if (!facultyRepository.existsById(id)) {
            return false;
        }

        facultyRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Faculty> filterFacultiesByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    @Override
    public List<Faculty> searchFacultiesByNameOrColorIgnoreCase(String searchString) {
        return facultyRepository.findByNameIgnoreCaseContainingOrColorIgnoreCaseContaining(searchString, searchString);
    }

    @Override
    public List<Student> getStudentsByFacultyId(long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty == null) {
            return Collections.emptyList();
        }
        return faculty.getStudents();
    }

    @Override
    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }


}
