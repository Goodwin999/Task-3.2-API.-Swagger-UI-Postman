package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Управление факультетами", description = "Методы для работы с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) throws DatabaseAccessException {
        Faculty createdFaculty = facultyService.create(faculty);
        return ResponseEntity.ok().body(createdFaculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.read(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(faculty);
    }

    @PutMapping("/")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.update(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        boolean deleted = facultyService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Faculty>> filterFacultiesByColor(@RequestParam String color) {
        List<Faculty> filteredFaculties = facultyService.filterFacultiesByColor(color);
        return ResponseEntity.ok().body(filteredFaculties);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Faculty>> searchFacultiesByNameOrColorIgnoreCase(@RequestParam String searchString) {
        List<Faculty> foundFaculties = facultyService.searchFacultiesByNameOrColorIgnoreCase(searchString);
        return ResponseEntity.ok().body(foundFaculties);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsByFaculty(@PathVariable long id) {
        List<Student> students = facultyService.getStudentsByFacultyId(id);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }
    @GetMapping("/longest-name")
    public ResponseEntity<String> getLongestFacultyName() {
        String longestName = facultyService.findLongestFacultyName();
        if (longestName == null) {
            return ResponseEntity.noContent().build(); // Если нет факультетов, возвращаем 204 No Content
        }
        return ResponseEntity.ok(longestName); // Возвращаем самое длинное название
    }


}




