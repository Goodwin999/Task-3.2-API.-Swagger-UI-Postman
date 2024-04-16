package ru.hogwarts.school.model;

import java.util.List;
import java.util.Objects;

public class Faculty {


    private Long id;
    private String name;
    private String color;
    private List<Student> studentList;

    public Faculty(Long id, String name, String color, List<Student> studentList) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.studentList = studentList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color) && Objects.equals(studentList, faculty.studentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, studentList);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", studentList=" + studentList +
                '}';
    }
}
