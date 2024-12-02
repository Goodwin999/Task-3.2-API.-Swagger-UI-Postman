package ru.hogwarts.school.controller;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(StudentController.class)
public class StudentControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarService avatarService;
    @MockBean
    FacultyService facultyService;

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student(1L, "Harry Potter", 17, null);
        Mockito.when(studentService.create(Mockito.any(Student.class))).thenReturn(student);
        mockMvc.perform(post("/student/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Harry Potter\",\"age\":17}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Harry Potter")))
                .andExpect(jsonPath("$.age", is(17)));
    }
    @Test
    void testGetStudent() throws Exception {
        Student student = new Student(1L, "Harry Potter", 17, null);
        Mockito.when(studentService.read(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Harry Potter")))
                .andExpect(jsonPath("$.age", is(17)));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student(1L, "Hermione Granger", 18, null);
        Mockito.when(studentService.update(Mockito.any(Student.class))).thenReturn(student);
        mockMvc.perform(put("/student/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Hermione Granger\",\"age\":18}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hermione Granger")))
                .andExpect(jsonPath("$.age", is(18)));
    }

    @Test
    void testDeleteStudent() throws Exception {
        Mockito.when(studentService.delete(1L)).thenReturn(true);
        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isNoContent()); // Проверяем, что статус 204 No Content
    }

    @Test
    void testFilterStudentsByAge() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "Harry Potter", 17, null),
                new Student(2L, "Ron Weasley", 17, null)
        );
        Mockito.when(studentService.filterStudentsByAge(17)).thenReturn(students);
        mockMvc.perform(get("/student/?age=17"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2))) // Проверяем, что вернулись два объекта
                .andExpect(jsonPath("$[0].name", is("Harry Potter")))
                .andExpect(jsonPath("$[1].name", is("Ron Weasley")));
    }

    @Test
    void testGetStudentFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        Student student = new Student(1L, "Harry Potter", 17, faculty);
        Mockito.when(studentService.read(1L)).thenReturn(student);
        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Gryffindor")));
    }
}
