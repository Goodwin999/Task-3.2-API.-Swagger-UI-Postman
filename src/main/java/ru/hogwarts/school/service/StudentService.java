package ru.hogwarts.school.service;

import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student create(Student student) throws DatabaseAccessException;

    Student read(long id);

    Student update(Student student);

    boolean delete(long id);
    List<Student> filterStudentsByAge(int age);
    Faculty getStudentFaculty(Long studentId);
    long getTotalStudentsCount();
    double getAverageStudentAge();
    List<Student> getLastFiveStudents();




}
