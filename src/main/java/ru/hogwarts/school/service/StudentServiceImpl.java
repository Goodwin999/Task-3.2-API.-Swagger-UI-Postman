package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
@Autowired
    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    this.facultyRepository = facultyRepository;
}

    @Override
    public Student create(Student student)  {
        logger.info("Вызван метод create для создания нового студента.");
        if (student.getFaculty() == null || student.getFaculty().getId() == null) {
            logger.error("Факультет студента не указан или некорректен.");
            throw new IllegalArgumentException("Факультет студента не может быть пустым или transient.");
        }
        Optional<Faculty> facultyOpt = facultyRepository.findById(student.getFaculty().getId());
        if (facultyOpt.isEmpty()) {
            logger.error("Факультет с id = {} не найден.", student.getFaculty().getId());
            throw new IllegalArgumentException("Указанный факультет не существует.");
        }
        student.setFaculty(facultyOpt.get());
        student.setId(null);
        Student savedStudent = studentRepository.save(student);
        logger.debug("Студент успешно создан: {}", savedStudent);
        return savedStudent;
    }


    @Override
    public Student read(long id) {
        logger.info("Вызван метод read для получения студента с id = {}", id);
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            logger.warn("Студент с id = {} не найден.", id);
            return null;
        }
        logger.debug("Студент найден: {}", studentOpt.get());
        return studentOpt.get();
    }


    @Override
    public Student update(Student updatedStudent) {
        logger.info("Был вызван метод update для обновления студента с id = {}", updatedStudent.getId());
        Optional<Student> studentOpt = studentRepository.findById(updatedStudent.getId());
        if (studentOpt.isEmpty()) {
            logger.warn("Студент с id = {} не найден. Обновление невозможно.", updatedStudent.getId());
            return null;
        }
        Student savedStudent = studentRepository.save(updatedStudent);
        logger.info("Студент с id = {} успешно обновлен.", savedStudent.getId());
        return savedStudent;
    }

    @Override
    public boolean delete(long id) {
        logger.info("Был вызван метод delete для удаления студента с id = {}", id);
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            logger.warn("Студент с id = {} не найден. Удаление невозможно.", id);
            return false;
        }
        studentRepository.deleteById(id);
        logger.info("Студент с id = {} успешно удален.", id);
        return true;
    }

    @Override
    public List<Student> filterStudentsByAge(int age) {
        logger.info("Был вызван метод filterStudentsByAge для получения студентов с возрастом = {}", age);
        List<Student> students = studentRepository.findByAge(age);
        logger.debug("Найдено {} студентов с возрастом {}", students.size(), age);
        return students;
    }

    @Override
    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Был вызван метод getStudentFaculty для получения факультета студента с id = {}", studentId);
        Student student = read(studentId);
        if (student == null) {
            logger.warn("Студент с id = {} не найден.", studentId);
            return null;
        }
        Faculty faculty = student.getFaculty();
        logger.debug("Факультет студента с id = {}: {}", studentId, faculty);
        return faculty;
    }

    @Override
    public long getTotalStudentsCount() {
        logger.info("Был вызван метод getTotalStudentsCount для подсчета общего количества студентов.");
        long count = studentRepository.countAllStudents();
        logger.debug("Общее количество студентов: {}", count);
        return count;
    }

    @Override
    public double getAverageStudentAge() {
        logger.info("Был вызван метод getAverageStudentAge для подсчета среднего возраста студентов.");
        double averageAge = studentRepository.getAverageAge();
        logger.debug("Средний возраст студентов: {}", averageAge);
        return averageAge;
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Был вызван метод getLastFiveStudents для получения последних пяти студентов.");
        List<Student> students = studentRepository.findLastFiveStudents();
        logger.debug("Последние пять студентов: {}", students);
        return students;
    }

    @Override
    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Был вызван метод findByAgeBetween для поиска студентов с возрастом между {} и {}", minAge, maxAge);
        if (minAge > maxAge) {
            logger.error("Минимальный возраст ({}) больше максимального ({}).", minAge, maxAge);
            throw new IllegalArgumentException("Минимальный возраст не может быть больше максимального.");
        }
        List<Student> students = studentRepository.findByAgeBetween(minAge, maxAge);
        logger.debug("Найдено {} студентов с возрастом между {} и {}", students.size(), minAge, maxAge);
        return students;
    }

}

