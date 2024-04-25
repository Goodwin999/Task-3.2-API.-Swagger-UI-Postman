package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Управление факультетами", description = "Методы для работы с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Operation(summary = "Создание нового факультета")
    @PostMapping("/create")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.create(faculty);
        return ResponseEntity.ok().body(createdFaculty);
    }

    @Operation(summary = "Получение факультета по ID")
    @GetMapping("/read/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.read(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(faculty);
    }

    @Operation(summary = "Обновление информации о факультете")
    @PutMapping("/update")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.update(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedFaculty);
    }

    @Operation(summary = "Удаление факультета по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        boolean deleted = facultyService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Фильтрация факультетов по цвету")
    @GetMapping("/filterByColor/{color}")
    public ResponseEntity<?> filterFacultiesByColor(@PathVariable String color) {
        List<Faculty> filteredFaculties = facultyService.filterFacultiesByColor(color);
        if (filteredFaculties.isEmpty()) {
            String message = "Факультеты с цветом " + color + " не найдены";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok().body(filteredFaculties);
        }
    }
}


