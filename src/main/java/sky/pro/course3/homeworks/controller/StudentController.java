package sky.pro.course3.homeworks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }
    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@PathVariable Integer age) {
        return ResponseEntity.ok(studentService.getStudentByAge(age));
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getStudentByAgeBetween(@RequestParam Integer startAge,
                                                                      @RequestParam Integer endAge) {
        return ResponseEntity.ok(studentService.getStudentByAgeBetween(startAge, endAge));
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getFacultyNameByStudentId(id));
    }

    @GetMapping("last")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }


}
