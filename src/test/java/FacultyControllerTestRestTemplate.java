import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SchoolApplication.class)
public class FacultyControllerTestRestTemplate {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Faculty testFaculty;

    @BeforeEach
    public void setup() {
        testFaculty = new Faculty();
        testFaculty.setName("Gryffindor");
        testFaculty.setColor("Red");
    }


    @Test
    public void testCreateFaculty() {
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty/", testFaculty, Faculty.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testFaculty.getName(), response.getBody().getName());
    }

    @Test
    public void testGetFaculty() {
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity("/faculty/", testFaculty, Faculty.class);
        Long facultyId = createResponse.getBody().getId();
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(facultyId, response.getBody().getId());
    }
    @Test
    public void testUpdateFaculty() {
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity("/faculty/", testFaculty, Faculty.class);
        Long facultyId = createResponse.getBody().getId();
        testFaculty.setName("Hufflepuff");
        restTemplate.put("/faculty/", testFaculty);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hufflepuff", response.getBody().getName());
    }
    @Test
    public void testDeleteFaculty() {
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity("/faculty/", testFaculty, Faculty.class);
        Long facultyId = createResponse.getBody().getId();
        restTemplate.delete("/faculty/" + facultyId);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertEquals(404, response.getStatusCodeValue());
    }
    @Test
    public void testFilterFacultiesByColor() {
        restTemplate.postForEntity("/faculty/", testFaculty, Faculty.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/?color=Red", Faculty.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Faculty faculty = response.getBody();
        assertEquals("Red", faculty.getColor());
    }
    @Test
    public void testSearchFacultiesByNameOrColor() {
        restTemplate.postForEntity("/faculty/", testFaculty, Faculty.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/search?searchString=gryffindor", Faculty.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue((BooleanSupplier) response.getBody());
        assertEquals("Gryffindor", response.getBody().getName());
    }
    @Test
    public void testGetStudentsByFaculty() {
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity("/faculty/", testFaculty, Faculty.class);
        Long facultyId = createResponse.getBody().getId();
        Student student = new Student();
        student.setName("Harry Potter");
        student.setAge(18);
        student.setFaculty(createResponse.getBody());
        restTemplate.postForEntity("/student/", student, Student.class);
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/faculty/" + facultyId + "/students", Student[].class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }
}
