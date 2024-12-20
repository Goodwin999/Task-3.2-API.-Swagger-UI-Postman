package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
@Autowired
    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    this.facultyRepository = facultyRepository;
}

    @Override
    public Student create(Student student)  {
        if (student.getFaculty() == null || student.getFaculty().getId() == null) {
            throw new IllegalArgumentException("Факультет студента не может быть пустым или transient.");
        }
        Optional<Faculty> facultyOpt = facultyRepository.findById(student.getFaculty().getId());
        if (facultyOpt.isEmpty()) {
            throw new IllegalArgumentException("Указанный факультет не существует.");
        }
        student.setFaculty(facultyOpt.get());
        student.setId(null);
        return studentRepository.save(student);
    }


    @Override
    public Student read(long id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            System.out.println("Студент с id " + id + " не найден.");
            return null;
        }
        return studentOpt.get();
    }


    @Override
    public Student update(Student updatedStudent) {
        Optional<Student> studentOpt = studentRepository.findById(updatedStudent.getId());
        if (studentOpt.isEmpty()) {
            System.out.println("Студент с id " + updatedStudent.getId() + " не найден. Обновление невозможно.");
            return null;
        }
        return studentRepository.save(updatedStudent);
    }

    @Override
    public boolean delete(long id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            System.out.println("Студент с id " + id + " не найден. Удаление невозможно.");
            return false;
        }
        studentRepository.deleteById(id);
        System.out.println("Студент с id " + id + " успешно удален.");
        return true;
    }

    @Override
    public List<Student> filterStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Faculty getStudentFaculty(Long studentId) {
        Student student = read(studentId);
        if (student == null) {
            System.out.println("Студент не найден по ID: " + studentId);  // Логируем
            return null;
        }
        return student.getFaculty();
    }

    @Override
    public long getTotalStudentsCount() {
        return studentRepository.countAllStudents();
    }

    @Override
    public double getAverageStudentAge() {
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.findLastFiveStudents();
    }

    @Override
    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        if (minAge > maxAge) {
            throw new IllegalArgumentException("Минимальный возраст не может быть больше максимального.");
        }
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

}

