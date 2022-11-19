package sky.pro.course3.homeworks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        LOGGER.info("Запущен метод createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        LOGGER.info("Запущен метод findFaculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        LOGGER.info("Запущен метод editFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        LOGGER.info("Запущен метод deleteFaculty");

        if (facultyRepository.existsById(id)) {
            LOGGER.debug("Удаляется факультет " + id);
            facultyRepository.deleteById(id);
        }

        LOGGER.error("Не найден факультет с id " + id);
    }

    public Collection<Faculty> getAllFaculty() {
        LOGGER.info("Запущен метод getAllFaculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        LOGGER.info("Запущен метод findFacultyByColorIgnoreCase");
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultyByName(String name) {
        LOGGER.info("Запущен метод getFacultyByName");
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Student> getStudentsByFacultyId(long id) {
        LOGGER.info("Запущен метод getStudentsByFacultyId");
        
        Faculty faculty = findFaculty(id);

        if (faculty == null) {
            LOGGER.warn("Не найдены студенты факультета с id " + id);
            return List.of();
        }
        return faculty.getStudents();
    }

    public String getFacultiesMaxLengthName() {
        LOGGER.info("Запущен метод getFacultiesMaxLengthName");
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length)).orElse("");
    }
}
