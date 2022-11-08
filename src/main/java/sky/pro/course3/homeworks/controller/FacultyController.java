package sky.pro.course3.homeworks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.service.FacultyService;

import java.util.Collection;


@RestController
@RequestMapping("faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);

        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty= facultyService.editFaculty(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }

    @GetMapping("/nameOrColor")
    public ResponseEntity<Collection<Faculty>> getFacultyByNameOrColor(@RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String color) {
        if (name != null) {
            return ResponseEntity.ok(facultyService.getFacultyByName(name));
        }

        if (color != null) {
            return ResponseEntity.ok(facultyService.getFacultyByColor(color));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsByFaculties(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getStudentsByFacultyId(id));
    }
}
