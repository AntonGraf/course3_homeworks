package sky.pro.course3.homeworks.service;

import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
        }
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultyByName(String name) {
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Student> getStudentsByFacultyId(long id) {
        Faculty faculty = findFaculty(id);

        if (faculty == null) {
            return List.of();
        }
        return faculty.getStudents();
    }
}
