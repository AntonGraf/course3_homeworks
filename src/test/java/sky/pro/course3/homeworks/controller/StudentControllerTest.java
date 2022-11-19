package sky.pro.course3.homeworks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertNotNull(studentController);
    }

    @Test
    void testGetStudents() {
        insertStudent();
        assertFalse(restTemplate.getForObject("http://localhost:" + port + "/students", List.class).isEmpty());
    }

    @Test
    void testGetStudentById() {
        Student testStudent = getTestStudent();
        long insertedId = insertStudent();
        testStudent.setId(insertedId);
        assertEquals(restTemplate.getForObject("http://localhost:" + port + "/students/" + insertedId, Student.class),
                testStudent);
    }

    @Test
    void testGetStudentByFailedId() {
        restTemplate.delete("http://localhost:" + port + "/students/1");
        assertNull(restTemplate.getForObject("http://localhost:" + port + "/students/1", Student.class));
    }

    @Test
    void testPostStudent() {
        Student testStudent = getTestStudent();
        Student insertedStudent = restTemplate.postForObject("http://localhost:" + port + "/students/", testStudent,
                Student.class);
        testStudent.setId(insertedStudent.getId());
        assertEquals(insertedStudent, testStudent);
    }

    @Test
    void testPutStudent() {
        Student testStudent = getTestStudent();
        long insertedId = insertStudent();

        testStudent.setAge(25);
        testStudent.setId(insertedId);

        restTemplate.put("http://localhost:" + port + "/students/", testStudent);
        assertEquals(restTemplate.getForObject("http://localhost:" + port + "/students/" + insertedId,
                Student.class), testStudent);

    }

    @Test
    void testDeleteStudent() {
        long insertedId = insertStudent();

        restTemplate.delete("http://localhost:" + port + "/students/" + insertedId);
        assertNull(restTemplate.getForObject("http://localhost:" + port + "/students/" + insertedId, Student.class));
    }

    @Test
    void testGetStudentByAge() {

        long insertedId = insertStudent();

        Student student = restTemplate.getForObject("http://localhost:" + port + "/students/" + insertedId,
                Student.class);

        int age = student.getAge();

        List<Student> students = List.of(restTemplate.getForObject("http://localhost:" + port + "/students/age/" + age,
                Student[].class));
        assertFalse(students.isEmpty());
        assertTrue(students.contains(student));

    }

    @Test
    void testGetStudentByFailedAge() {

        long insertedId = insertStudent();

        Student student = restTemplate.getForObject("http://localhost:" + port + "/students/" + insertedId,
                Student.class);

        int age = student.getAge();

        List<Student> students = List.of(restTemplate.getForObject("http://localhost:" + port + "/students/age/" +
                        age * age,
                Student[].class));
        assertTrue(students.isEmpty());
        assertFalse(students.contains(student));

    }

    @Test
    void testGetStudentByBetweenAge() {

        long insertedId = insertStudent();

        Student student = restTemplate.getForObject("http://localhost:" + port + "/students/" + insertedId,
                Student.class);

        int age = student.getAge();

        List<Student> students = List.of(restTemplate.getForObject("http://localhost:" + port +
                        "/students/age?startAge=" + (age - 5) + "&endAge=" + (age + 5),
                Student[].class));
        assertFalse(students.isEmpty());
        assertTrue(students.contains(student));

    }

    @Test
    void testGetStudentByFailedBetweenAge() {

        long insertedId = insertStudent();

        Student student = restTemplate.getForObject("http://localhost:" + port + "/students/" + insertedId,
                Student.class);

        int age = student.getAge();

        List<Student> students = List.of(restTemplate.getForObject("http://localhost:" + port +
                        "/students/age?startAge=" + (age + 5) + "&endAge=" + (age - 5),
                Student[].class));
        assertTrue(students.isEmpty());
        assertFalse(students.contains(student));

    }

    private long insertStudent() {
        Student testStudent = getTestStudent();
        Student insertedStudent = restTemplate.postForObject("http://localhost:" + port + "/students/", testStudent,
                Student.class);
        return insertedStudent.getId();
    }
    private Student getTestStudent() {
        Student testStudent = new Student();
        testStudent.setId(1);
        testStudent.setName("Гарри");
        testStudent.setAge(18);
        testStudent.setFaculty(getTestFaculty());

        return testStudent;
    }

    private Faculty getTestFaculty() {
        Faculty testFaculty = new Faculty();
        testFaculty.setId(1);
        testFaculty.setColor("оранжевый");
        testFaculty.setName("Гриффиндор");

        return testFaculty;
    }

}