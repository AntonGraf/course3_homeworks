package sky.pro.course3.homeworks.service;

import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Faculty;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();

    private long id = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++id);
        faculties.put(faculty.getId(), faculty);
        return faculties.get(faculty.getId());
    }

    public Faculty getFaculty(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        return null;
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        if (faculties.containsKey(id)) {
            faculties.put(id, faculty);
            return getFaculty(id);
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        return null;
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> getAllFaculty() {
        return faculties.values();
    }
}
