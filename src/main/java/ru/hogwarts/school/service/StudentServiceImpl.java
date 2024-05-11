package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) throws DatabaseAccessException {
        try {
            if (student.getName() == null || student.getName().isEmpty()) {
                throw new IllegalArgumentException("Имя не может быть пустым или не указанным.");
            }
            student.setId(null);
            return studentRepository.save(student);
        } catch (Exception ex) {
            throw new DatabaseAccessException("Ошибка доступа к базе данных при создании студента: " + ex.getMessage(), ex);
        }
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
}
