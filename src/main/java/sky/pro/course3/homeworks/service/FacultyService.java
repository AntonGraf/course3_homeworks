package sky.pro.course3.homeworks.service;

import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.repository.FacultyRepository;

import java.util.Collection;

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

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }
}
