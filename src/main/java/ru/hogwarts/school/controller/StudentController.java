package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@Tag(name = "Управление студентами", description = "Методы для работы со студентами")
public class StudentController {
    private final StudentService studentService;
    private  final FacultyService facultyService;


    @Autowired
    public StudentController(StudentService studentService, FacultyService facultyService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
    }

    @PostMapping("/")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws DatabaseAccessException {
        if (student.getFaculty() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Faculty> facultyOpt = facultyService.findById(student.getFaculty().getId());
        if (facultyOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        // Если проверки прошли, сохраняем студента
        student.setFaculty(facultyOpt.get());
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
        List<Student> students = studentService.findByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable("id") long studentId) {
        Faculty faculty = studentService.getStudentFaculty(studentId);  // Используем сервис
        if (faculty == null) {
            System.out.println("Факультет имеет значение null для StudentId: " + studentId);
            return ResponseEntity.notFound().build();  // Проверяем, если факультет пустой
        }
        return ResponseEntity.ok().body(faculty);  // Возвращаем факультет
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalStudentsCount() {
        long count = studentService.getTotalStudentsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageStudentAge() {
        double averageAge = studentService.getAverageStudentAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        List<Student> lastFiveStudents = studentService.getLastFiveStudents();
        return ResponseEntity.ok(lastFiveStudents);
    }
    @GetMapping("/names-starting-with-a")
    public ResponseEntity<List<String>> getStudentNamesStartingWithA() {
        List<String> studentNames = studentService.findAllStudentNamesStartingWithA();
        return ResponseEntity.ok(studentNames);
    }
    @GetMapping("/average-age-stream")
    public ResponseEntity<Double> getAverageStudentAgeWithStreams() {
        double averageAge = studentService.getAverageStudentAgeWithStreams();
        return ResponseEntity.ok(averageAge);
    }


}