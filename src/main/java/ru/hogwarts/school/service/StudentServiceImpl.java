package ru.hogwarts.school.service;

import ru.hogwarts.school.exception.DatabaseAccessException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        studentMap.put(id, student);
        return student;
    }

    @Override
    public Student read(long id) {
        Student student = studentMap.get(id);
        if (student == null) {
            System.out.println("Студент с id " + id + " не найден.");
        }
        return student;
    }

    @Override
    public Student update(Student updatedStudent) {
        Long id = updatedStudent.getId();
        if (!studentMap.containsKey(id)) {
            System.out.println("Студент с id " + id + " не найден. Обновление невозможно.");
        }
        studentMap.put(id, updatedStudent);
        return updatedStudent;
    }

    @Override
    public Student delete(long id) {
        boolean confirmationRequired = true;

        if (confirmationRequired) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Вы уверены, что хотите удалить студента с id " + id + "? (yes/no): ");
            String confirmation = scanner.nextLine().trim();
            if (!confirmation.equalsIgnoreCase("yes")) {
                System.out.println("Удаление отменено.");
                return null;
            }
        }

        try {
            Student removedStudent = studentMap.remove(id);
            if (removedStudent == null) {
                System.out.println("Студент с id " + id + " не найден. Удаление невозможно.");
            } else {
                System.out.println("Студент с id " + id + " успешно удален.");
            }
            return removedStudent;
        } catch (DatabaseAccessException e) {
            System.out.println("Невозможно удалить студента из-за отсутствия доступа к базе данных по техническим причинам.");
            e.printStackTrace();
            return null;

        }
    }
}


