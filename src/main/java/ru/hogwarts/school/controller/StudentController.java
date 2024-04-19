package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Student Management", description = "Endpoints for managing students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @Operation(summary = "Создать нового студента")
    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.create(student);
        return ResponseEntity.ok().body(createdStudent);
    }
    @Operation(summary = "Получить студента по ID")
    @GetMapping("/read/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.read(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(student);
    }
    @Operation(summary = "Обновить существующего студента ")
    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedStudent);
    }
    @Operation(summary = "Удалить студента по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        Student deletedStudent = studentService.delete(id);
        if (deletedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Фильтровать студентов по возрасту")
    @GetMapping("/filterByAge/{age}")
    public ResponseEntity<List<Student>> filterStudentsByAge(@PathVariable int age) {
        List<Student> filteredStudents = studentService.filterStudentsByAge(age);
        if (filteredStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(filteredStudents);
        }
    }
}