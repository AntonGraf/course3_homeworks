package sky.pro.course3.homeworks.service;

import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    private long id = 0;

    public Student createStudent(Student student) {
        student.setId(++id);
        students.put(student.getId(), student);
        return students.get(student.getId());
    }

    public Student getStudent(Long id) {

        if (students.containsKey(id)) {
            return students.get(id);
        }
        return null;
    }

    public Student updateStudent(Long id, Student student) {

        if (students.containsKey(id)) {
            students.put(id, student);
            return students.get(id);
        }
        return null;

    }

    public Student deleteStudent(Long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        return null;
    }

    public Collection<Student> getStudentByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getAllStudent() {
        return students.values();
    }


}
