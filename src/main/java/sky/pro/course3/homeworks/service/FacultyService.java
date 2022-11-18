package sky.pro.course3.homeworks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Запущен метод createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Запущен метод findFaculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Запущен метод editFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Запущен метод deleteFaculty");

        if (facultyRepository.existsById(id)) {
            logger.debug("Удаляется факультет " + id);
            facultyRepository.deleteById(id);
        }

        logger.error("Не найден факультет с id " + id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("Запущен метод getAllFaculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Запущен метод findFacultyByColorIgnoreCase");
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultyByName(String name) {
        logger.info("Запущен метод getFacultyByName");
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Student> getStudentsByFacultyId(long id) {
        logger.info("Запущен метод getStudentsByFacultyId");
        
        Faculty faculty = findFaculty(id);

        if (faculty == null) {
            logger.warn("Не найдены студенты факультета с id " + id);
            return List.of();
        }
        return faculty.getStudents();
    }
}
