package sky.pro.course3.homeworks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService out;

    StudentServiceTest() {
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

        when(studentRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllStudent().isEmpty());

        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(out.createStudent(student), student);

        when(studentRepository.findAll()).thenReturn(List.of(student));
        assertEquals(out.getAllStudent().size(), 1);
        assertTrue(out.getAllStudent().contains(student));
    }

    @Test
    void getStudent() {

        when(studentRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllStudent().isEmpty());
        assertNull(out.findStudent(3));

        List<Student> studentList = getStudents();
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(out.getAllStudent().size(),4);

        Student exceptedStudent = new Student("Иван", 20);
        exceptedStudent.setId(2L);
        when(studentRepository.findById(2L)).thenReturn(Optional.ofNullable(studentList.get(1)));
        assertEquals(out.findStudent(2L), exceptedStudent);
        exceptedStudent.setId(1L);
        assertNotEquals(out.findStudent(2L), exceptedStudent);

        assertNull(out.findStudent(5));

    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void updateStudent(Student student) {

        when(studentRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllStudent().isEmpty());

        List<Student> studentList = getStudents();
        when(studentRepository.findAll()).thenReturn(studentList);

        student.setId(2L);
        when(studentRepository.save(student)).thenReturn(student);
        studentList.get(1).setAge(student.getAge());
        studentList.get(1).setName(student.getName());
        assertEquals(out.editStudent(student), student);

        assertTrue(out.getAllStudent().contains(student));

    }

    @Test
    void deleteStudent() {

        when(studentRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllStudent().isEmpty());

        when(studentRepository.findAll()).thenReturn(getStudents());
        int countStudents = out.getAllStudent().size();
        out.deleteStudent(3L);

        when(studentRepository.findAll()).thenReturn(getStudents().subList(0, getStudents().size() - 1));
        assertEquals(out.getAllStudent().size(), countStudents - 1);


    }

    @Test
    void getStudentByAge() {

        when(studentRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllStudent().isEmpty());
        when(studentRepository.findStudentsByAge(20)).thenReturn(List.of());
        assertTrue(out.getStudentByAge(20).isEmpty());

        List<Student> studentList = getStudents();
        when(studentRepository.findAll()).thenReturn(studentList);

        assertFalse(out.getAllStudent().isEmpty());

        Student exceptedStudent = new Student("Ольга", 23);
        exceptedStudent.setId(3L);
        when(studentRepository.findStudentsByAge(23)).thenReturn(List.of(exceptedStudent));
        assertTrue(out.getStudentByAge(23).contains(exceptedStudent));
        assertTrue(out.getAllStudent().containsAll(out.getStudentByAge(20)));
    }

    public List<Student> getStudents() {
        List<Student> studentList = List.of(
                new Student("Сергей", 25),
                new Student("Иван", 20),
                new Student("Ольга", 23),
                new Student("Екатерина", 20)
        );

        for (int i = 0; i < studentList.size(); i++) {
            studentList.get(i).setId(i + 1);
        }
        return studentList;

    }
}