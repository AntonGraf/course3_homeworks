package sky.pro.course3.homeworks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sky.pro.course3.homeworks.model.Faculty;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {

    private final FacultyService out = new FacultyService();

    @BeforeEach
    void setUp() {
        out.getAllFaculty().clear();
    }

    public static Stream<Arguments> provideParamsTest() {
        return Stream.of(
                Arguments.of(new Faculty("Гриффиндор", "оранжевый")),
                Arguments.of(new Faculty("Слизерин", "зеленый")),
                Arguments.of(new Faculty("Когтевран", "зеленый"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void createFaculty(Faculty faculty) {

        assertTrue(out.getAllFaculty().isEmpty());
        assertEquals(out.createFaculty(faculty), faculty);
        assertEquals(out.getAllFaculty().size(), 1);
        assertTrue(out.getAllFaculty().contains(faculty));
    }

    @Test
    void getFaculty() {

        assertTrue(out.getAllFaculty().isEmpty());
        assertNull(out.getFaculty(3L));

        fillFaculties();
        assertEquals(out.getAllFaculty().size(),4);

        Faculty exceptedFaculty = new Faculty("Слизерин", "зеленый");
        exceptedFaculty.setId(2);
        assertEquals(out.getFaculty(2L), exceptedFaculty);
        exceptedFaculty.setId(1);
        assertNotEquals(out.getFaculty(2L), exceptedFaculty);

        assertNull(out.getFaculty(5L));

    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void updateFaculty(Faculty faculty) {

        assertTrue(out.getAllFaculty().isEmpty());

        fillFaculties();

        faculty.setId(2);
        assertEquals(out.updateFaculty(2L, faculty), faculty);
        assertTrue(out.getAllFaculty().contains(faculty));

    }

    @Test
    void deleteFaculty() {

        assertTrue(out.getAllFaculty().isEmpty());
        assertNull(out.deleteFaculty(1L));

        fillFaculties();

        int countFaculties = out.getAllFaculty().size();
        Faculty exceptedFaculty = new Faculty("Когтевран", "зеленый");
        exceptedFaculty.setId(3);
        assertEquals(out.deleteFaculty(3L), exceptedFaculty);
        assertEquals(out.getAllFaculty().size(), countFaculties - 1);


    }

    @Test
    void getFacultyByColor() {

        assertTrue(out.getAllFaculty().isEmpty());
        assertTrue(out.getFacultyByColor("синий").isEmpty());

        fillFaculties();

        assertFalse(out.getAllFaculty().isEmpty());

        Faculty exceptedFaculty = new Faculty("Пуффендуй", "желтый");
        exceptedFaculty.setId(4);
        assertTrue(out.getFacultyByColor("желтый").contains(exceptedFaculty));
        assertTrue(out.getAllFaculty().containsAll(out.getFacultyByColor("зеленый")));
    }

    public void fillFaculties() {
        out.createFaculty(new Faculty("Гриффиндор", "оранжевый"));
        out.createFaculty(new Faculty("Слизерин", "зеленый"));
        out.createFaculty(new Faculty("Когтевран", "зеленый"));
        out.createFaculty(new Faculty("Пуффендуй", "желтый"));
    }
}