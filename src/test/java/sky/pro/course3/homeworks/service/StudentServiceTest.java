package sky.pro.course3.homeworks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sky.pro.course3.homeworks.model.Student;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private final StudentService out = new StudentService();

    @BeforeEach
    void setUp() {
        out.getAllStudent().clear();
    }

    public static Stream<Arguments> provideParamsTest() {
        return Stream.of(
                Arguments.of(new Student("Сергей", 25)),
                Arguments.of(new Student("Иван", 20)),
                Arguments.of(new Student("Ольга", 23))
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void createStudent(Student student) {

        assertTrue(out.getAllStudent().isEmpty());
        assertEquals(out.createStudent(student), student);
        assertEquals(out.getAllStudent().size(), 1);
        assertTrue(out.getAllStudent().contains(student));
    }

    @Test
    void getStudent() {

        assertTrue(out.getAllStudent().isEmpty());
        assertNull(out.getStudent(3L));

        fillStudents();
        assertEquals(out.getAllStudent().size(),4);

        Student exceptedStudent = new Student("Иван", 20);
        exceptedStudent.setId(2);
        assertEquals(out.getStudent(2L), exceptedStudent);
        exceptedStudent.setId(1);
        assertNotEquals(out.getStudent(2L), exceptedStudent);

        assertNull(out.getStudent(5L));

    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void updateStudent(Student student) {

        assertTrue(out.getAllStudent().isEmpty());

        fillStudents();

        student.setId(2);
        assertEquals(out.updateStudent(2L, student), student);
        assertTrue(out.getAllStudent().contains(student));

    }

    @Test
    void deleteStudent() {

        assertTrue(out.getAllStudent().isEmpty());
        assertNull(out.deleteStudent(1L));

        fillStudents();

        int countStudents = out.getAllStudent().size();
        Student exceptedStudent = new Student("Ольга", 23);
        exceptedStudent.setId(3);
        assertEquals(out.deleteStudent(3L), exceptedStudent);
        assertEquals(out.getAllStudent().size(), countStudents - 1);


    }

    @Test
    void getStudentByAge() {

        assertTrue(out.getAllStudent().isEmpty());
        assertTrue(out.getStudentByAge(20).isEmpty());

        fillStudents();

        assertFalse(out.getAllStudent().isEmpty());

        Student exceptedStudent = new Student("Ольга", 23);
        exceptedStudent.setId(3);
        assertTrue(out.getStudentByAge(23).contains(exceptedStudent));
        assertTrue(out.getAllStudent().containsAll(out.getStudentByAge(20)));
    }

    public void fillStudents() {
        out.createStudent(new Student("Сергей", 25));
        out.createStudent(new Student("Иван", 20));
        out.createStudent(new Student("Ольга", 23));
        out.createStudent(new Student("Екатерина", 20));
    }
}