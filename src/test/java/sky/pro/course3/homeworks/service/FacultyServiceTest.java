package sky.pro.course3.homeworks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyService out;

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

        when(facultyRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllFaculty().isEmpty());

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        assertEquals(out.createFaculty(faculty), faculty);

        when(facultyRepository.findAll()).thenReturn(List.of(faculty));
        assertEquals(out.getAllFaculty().size(), 1);
        assertTrue(out.getAllFaculty().contains(faculty));
    }

    @Test
    void getFaculty() {

        when(facultyRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllFaculty().isEmpty());
        assertNull(out.findFaculty(3));

        when(facultyRepository.findAll()).thenReturn(getFaculties());
        assertEquals(out.getAllFaculty().size(),4);

        Faculty exceptedFaculty = new Faculty("Слизерин", "зеленый");
        exceptedFaculty.setId(2L);
        when(facultyRepository.findById(2L)).thenReturn(Optional.of(getFaculties().get(1)));
        assertEquals(out.findFaculty(2), exceptedFaculty);
        exceptedFaculty.setId(1);
        assertNotEquals(out.findFaculty(2), exceptedFaculty);

        assertNull(out.findFaculty(5));

    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void updateFaculty(Faculty faculty) {

        when(facultyRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllFaculty().isEmpty());

        List<Faculty> facultyList = getFaculties();
        when(facultyRepository.findAll()).thenReturn(facultyList);

        faculty.setId(2);
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        facultyList.get(1).setColor(faculty.getColor());
        facultyList.get(1).setName(faculty.getName());
        assertEquals(out.editFaculty(faculty), faculty);

        assertTrue(out.getAllFaculty().contains(faculty));

    }

    @Test
    void deleteFaculty() {

        when(facultyRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllFaculty().isEmpty());

        when(facultyRepository.findAll()).thenReturn(getFaculties());
        int countFaculties = out.getAllFaculty().size();
        out.deleteFaculty(3L);

        when(facultyRepository.findAll()).thenReturn(getFaculties().subList(0, getFaculties().size() - 1));
        assertEquals(out.getAllFaculty().size(), countFaculties - 1);


    }

    @Test
    void getFacultyByColor() {

        when(facultyRepository.findAll()).thenReturn(List.of());
        assertTrue(out.getAllFaculty().isEmpty());
        when(facultyRepository.findFacultyByColor("синий")).thenReturn(List.of());
        assertTrue(out.getFacultyByColor("синий").isEmpty());

        List<Faculty> facultyList = getFaculties();
        when(facultyRepository.findAll()).thenReturn(facultyList);
        assertFalse(out.getAllFaculty().isEmpty());

        Faculty exceptedFaculty = new Faculty("Пуффендуй", "желтый");
        exceptedFaculty.setId(4);
        when(facultyRepository.findFacultyByColor("желтый")).thenReturn(List.of(exceptedFaculty));
        assertTrue(out.getFacultyByColor("желтый").contains(exceptedFaculty));

        when(facultyRepository.findFacultyByColor("зеленый")).thenReturn(facultyList.stream()
                .filter(faculty -> faculty.getColor().equals("зеленый")).collect(Collectors.toList()));
        assertTrue(out.getAllFaculty().containsAll(out.getFacultyByColor("зеленый")));
    }

    public List<Faculty> getFaculties() {
        List<Faculty> facultyList = List.of(
                new Faculty("Гриффиндор", "оранжевый"),
                new Faculty("Слизерин", "зеленый"),
                new Faculty("Когтевран", "зеленый"),
                new Faculty("Пуффендуй", "желтый")
        );

        for (int i = 0; i < facultyList.size(); i++) {
            facultyList.get(i).setId(i + 1);
        }
        return facultyList;
    }
}