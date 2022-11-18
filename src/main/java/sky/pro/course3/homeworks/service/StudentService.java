package sky.pro.course3.homeworks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Запущен метод createStudent");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Запущен метод findStudent");
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.info("Запущен метод editStudent");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Запущен метод deleteStudent");
        logger.debug("Удаляется студент с id " + id);

        if (studentRepository.existsById(id)){
            studentRepository.deleteById(id);
        }

        logger.error("Не удалось удалить студента. Не найдет студент " + id);

    }

    public Collection<Student> getAllStudent() {
        logger.info("Запущен метод getAllStudent");
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentByAge(int age) {
        logger.info("Запущен метод getStudentByAge");
        return studentRepository.findStudentsByAge(age);
    }

    public Collection<Student> getStudentByAgeBetween(int startAge, int endAge) {
        logger.info("Запущен метод getStudentByAgeBetween");
        return studentRepository.findStudentsByAgeBetween(startAge, endAge);
    }

    public Faculty getFacultyNameByStudentId(long id) {
        logger.info("Запущен метод getFacultyNameByStudentId");

        Student student = findStudent(id);

        if (student == null) {
            logger.warn("Не найден студент с id " + id);
            return null;
        }

        return student.getFaculty();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("Запущен метод getLastFiveStudents");
        return studentRepository.getLastFiveStudents();
    }

    public Long getStudentsCount() {
        logger.info("Запущен метод getStudentsCount");
        return studentRepository.getStudentsCountAndAgeAverage().getStudentsCount();
    }

    public Double getStudentsAgeAverage() {
        logger.info("Запущен метод getStudentsAgeAverage");
        return studentRepository.getStudentsCountAndAgeAverage().getStudentsAgeAverage();
    }

    public Collection<String> getStudentsWithNameStartA() {
        logger.info("Запущен метод getStudentsWithNameStartA");
        return studentRepository.findAll()
                .stream()
                .parallel()
                .filter(student -> student.getName().toUpperCase().startsWith("А"))
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

}
