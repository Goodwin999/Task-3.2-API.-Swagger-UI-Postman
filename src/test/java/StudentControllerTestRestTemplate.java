
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
    private Faculty testFaculty;


    @BeforeEach
    public void setup() {

        testFaculty = new Faculty();
        testFaculty.setId(1L);
        testFaculty.setName("Кащенко");
        testFaculty.setColor("Red");
        testFaculty = facultyRepository.save(testFaculty);

        System.out.println("Сохраненный факультет: " + testFaculty);

        testStudent = new Student();
        testStudent.setName("Подлый Раджа");
        testStudent.setAge(18);
        testStudent.setFaculty(testFaculty);
        testStudent = studentRepository.save(testStudent);

        System.out.println("Созданный студент: " + testStudent);
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
        assertEquals(200, createResponse.getStatusCodeValue());  // Статус ответа должен быть 200 OK
        assertNotNull(createResponse.getBody());  // Тело ответа не должно быть null
        Long studentId = createResponse.getBody().getId();
        assertNotNull(studentId);  // ID студента не должен быть null

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
            ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/age?minAge=18&maxAge=20", Student[].class);
            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

    @Test
    public void testGetStudentFaculty() {
        // Сохраняем студента напрямую через репозиторий
        Student createdStudent = studentRepository.save(testStudent); // Сохраняем студента с привязанным факультетом
        Long studentId = createdStudent.getId(); // Получаем ID студента

        // Проверяем, что факультет студента правильный через эндпоинт
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/faculty/" + studentId, Faculty.class);

        // Проверяем, что статус ответа - 200 OK
        assertEquals(200, response.getStatusCodeValue());
        // Проверяем, что факультет не null
        assertNotNull(response.getBody());
        // Проверяем, что факультет из ответа совпадает с факультетом, установленным в setup()
        assertEquals(testFaculty.getId(), response.getBody().getId());
    }


}
