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
import ru.hogwarts.school.service.FacultyService;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        Mockito.when(facultyService.create(Mockito.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Gryffindor\", \"color\":\"Red\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Gryffindor")))
                .andExpect(jsonPath("$.color", is("Red")));
    }

    @Test
    void testGetFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        Mockito.when(facultyService.read(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Gryffindor")))
                .andExpect(jsonPath("$.color", is("Red")));
    }

    @Test
    void testUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Hufflepuff", "Yellow");
        Mockito.when(facultyService.update(Mockito.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"Hufflepuff\", \"color\":\"Yellow\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hufflepuff")))
                .andExpect(jsonPath("$.color", is("Yellow")));
    }

    @Test
    void testDeleteFaculty() throws Exception {
        Mockito.when(facultyService.delete(1L)).thenReturn(true);
        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isNoContent()); // Проверяем, что статус 204 No Content
    }

    @Test
    void testFilterFacultiesByColor() throws Exception {
        List<Faculty> faculties = Arrays.asList(
                new Faculty(1L, "Gryffindor", "Red"),
                new Faculty(2L, "Slytherin", "Green")
        );
        Mockito.when(facultyService.filterFacultiesByColor("Red")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/?color=Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2))) // Проверяем, что вернулись два факультета
                .andExpect(jsonPath("$[0].name", is("Gryffindor")))
                .andExpect(jsonPath("$[1].name", is("Slytherin")));
    }

    @Test
    void testSearchFacultiesByNameOrColor() throws Exception {
        List<Faculty> faculties = Arrays.asList(
                new Faculty(1L, "Gryffindor", "Red"),
                new Faculty(2L, "Hufflepuff", "Yellow")
        );
        Mockito.when(facultyService.searchFacultiesByNameOrColorIgnoreCase("red")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/search?searchString=red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2))) // Проверяем, что вернулись два факультета
                .andExpect(jsonPath("$[0].name", is("Gryffindor")))
                .andExpect(jsonPath("$[1].name", is("Hufflepuff")));
    }
    @Test
    void testGetStudentsByFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        Student student = new Student(1L, "Harry Potter", 17, faculty);
        faculty.setStudents(Arrays.asList(student));

        Mockito.when(facultyService.read(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1))) // Проверяем, что вернулся один студент
                .andExpect(jsonPath("$[0].name", is("Harry Potter"))); // Проверяем имя студента
    }

}