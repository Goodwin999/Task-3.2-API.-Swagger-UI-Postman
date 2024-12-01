
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SchoolApplication.class)
public class StudentControllerTestRestTemplate {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentService studentService;
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




}