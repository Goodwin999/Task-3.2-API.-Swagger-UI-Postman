package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

@Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        Optional<Faculty> existingFaculty = facultyRepository.findAll().stream()
                .filter(f -> f.getName().equalsIgnoreCase(faculty.getName()))
                .findFirst();

        if (existingFaculty.isPresent()) {
            throw new IllegalArgumentException("Факультет с таким именем уже существует.");
        }

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(long id){
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            throw new IllegalArgumentException("Факультет с id " + faculty.getId() + " не найден.");
        }

        return facultyRepository.save(faculty);
    }

    @Override
    public boolean delete(long id) {
        if (!facultyRepository.existsById(id)) {
            throw new IllegalArgumentException("Факультет с id " + id + " не найден.");
        }

        facultyRepository.deleteById(id);
        return true;
    }

    public List<Faculty> filterFacultiesByColor(String color) {
        List<Faculty> allFaculties = facultyRepository.findAll();
        List<Faculty> filteredFaculties = new ArrayList<>();

        for (Faculty faculty : allFaculties) {
            if (faculty.getColor().equalsIgnoreCase(color)) {
                filteredFaculties.add(faculty);
            }
        }

        return filteredFaculties;
    }
}
