package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentServiceImpl implements StudentService {


    private Map<Long, Student> studentMap = new HashMap<>();
    private long studentIdCounter = 0;

    @Override
    public Student create(Student student) {
        Long id = ++studentIdCounter;
        String name = student.getName();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым или не указанным.");
        }
        student.setId(id);
        studentMap.put(id,student);
        return student;
    }

    @Override
    public Student read(long id) {

        return null;
    }

    @Override
    public Student update(Student student) {

        return null;
    }

    @Override
    public Student delete(long id) {

        return null;
    }
}
