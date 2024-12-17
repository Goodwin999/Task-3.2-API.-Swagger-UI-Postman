
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
import ru.hogwarts.school.service.StudentService;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SchoolApplication.class)
public class StudentControllerTestRestTemplate {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentService studentService;
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;
    private Student testStudent;


    @BeforeEach
    public void setup() {
        testStudent = new Student();
        testStudent.setName("Harry Potter");
        testStudent.setAge(18);
        testStudent.setFaculty(null);
    }

    @Test
    public void testCreateStudent() {
        ResponseEntity<Student> response = restTemplate.postForEntity("/student/", testStudent, Student.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testStudent.getName(), response.getBody().getName());
    }

    @Test
    public void testGetStudent() {
        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/student/", testStudent, Student.class);
        Long studentId = createResponse.getBody().getId();
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + studentId, Student.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(studentId, response.getBody().getId());
    }

    @Test
    public void testUpdateStudent() {
        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/student/", testStudent, Student.class);
        Long studentId = createResponse.getBody().getId();
        testStudent.setName("Harry Potter");
        restTemplate.put("/student/", testStudent);
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + studentId, Student.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Harry Potter", response.getBody().getName());
    }

    @Test
    public void testDeleteStudent() {
        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/student/", testStudent, Student.class);
        Long studentId = createResponse.getBody().getId();
        restTemplate.delete("/student/" + studentId);
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + studentId, Student.class);
        assertEquals(404, response.getStatusCodeValue());

    }
        @Test
        public void testFilterStudentsByAge() {
            restTemplate.postForEntity("/student/", testStudent, Student.class);
            ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/?age=18", Student[].class);
            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
            assertEquals("Harry Potter", response.getBody()[0].getName());
        }

        @Test
        public void testGetStudentsByAgeRange() {
            restTemplate.postForEntity("/student/", testStudent, Student.class);
            ResponseEntity<Student> response = restTemplate.getForEntity("/student/age?minAge=18&maxAge=20",  Student.class);
            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
            assertTrue((BooleanSupplier) response.getBody());
        }

        @Test
        public void testGetStudentFaculty() {
            ResponseEntity<Student> createResponse = restTemplate.postForEntity("/student/", testStudent, Student.class);
            Long studentId = createResponse.getBody().getId();
            ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/faculty/" + studentId, Faculty.class);
            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
        }
    }