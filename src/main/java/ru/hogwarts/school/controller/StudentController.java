package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Управление студентами", description = "Методы для работы со студентами")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws DatabaseAccessException {
        Student createdStudent = studentService.create(student);
        return ResponseEntity.ok().body(createdStudent);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.read(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(student);
    }
    @PutMapping("/")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedStudent);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        boolean deleted = studentService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Student>> filterStudentsByAge(@RequestParam int age) {
        List<Student> filteredStudents = studentService.filterStudentsByAge(age);
        return ResponseEntity.ok().body(filteredStudents);
    }
    @GetMapping("/age")
    public ResponseEntity<List<Student>> getStudentsByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        List<Student> students = studentRepository.findByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok().body(students);
    }
    @GetMapping("/faculty/{studentId}")
        public ResponseEntity<Faculty> getStudentFaculty(@PathVariable long id) {
            Student student = studentService.read(id);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            Faculty faculty = student.getFaculty();
            return ResponseEntity.ok().body(faculty);
    }
}