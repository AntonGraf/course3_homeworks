package sky.pro.course3.homeworks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        LOGGER.info("Запущен метод createStudent");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        LOGGER.info("Запущен метод findStudent");
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        LOGGER.info("Запущен метод editStudent");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        LOGGER.info("Запущен метод deleteStudent");
        LOGGER.debug("Удаляется студент с id " + id);

        if (studentRepository.existsById(id)){
            studentRepository.deleteById(id);
        }

        LOGGER.error("Не удалось удалить студента. Не найдет студент " + id);

    }

    public Collection<Student> getAllStudent() {
        LOGGER.info("Запущен метод getAllStudent");
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentByAge(int age) {
        LOGGER.info("Запущен метод getStudentByAge");
        return studentRepository.findStudentsByAge(age);
    }

    public Collection<Student> getStudentByAgeBetween(int startAge, int endAge) {
        LOGGER.info("Запущен метод getStudentByAgeBetween");
        return studentRepository.findStudentsByAgeBetween(startAge, endAge);
    }

    public Faculty getFacultyNameByStudentId(long id) {
        LOGGER.info("Запущен метод getFacultyNameByStudentId");

        Student student = findStudent(id);

        if (student == null) {
            LOGGER.warn("Не найден студент с id " + id);
            return null;
        }

        return student.getFaculty();
    }

    public Collection<Student> getLastFiveStudents() {
        LOGGER.info("Запущен метод getLastFiveStudents");
        return studentRepository.getLastFiveStudents();
    }

    public Long getStudentsCount() {
        LOGGER.info("Запущен метод getStudentsCount");
        return studentRepository.getStudentsCountAndAgeAverage().getStudentsCount();
    }

    public Double getStudentsAgeAverage() {
        LOGGER.info("Запущен метод getStudentsAgeAverage");
        return studentRepository.getStudentsCountAndAgeAverage().getStudentsAgeAverage();
    }

    public Collection<String> getStudentNamesWIthAStart() {
        LOGGER.info("Запущен метод getStudentNamesWIthAStart");
        return studentRepository.findAll()
                .stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.toUpperCase().startsWith("А"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getStudentsAverageAge() {
        LOGGER.info("Запущен метод getStudentsAverageAge()");
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average().orElse(0);

    }

    public void printStudentWithThread() {
        LOGGER.info("Запущен метод printStudentWithThread()");

        List<Student> students = studentRepository.findAll();

        Thread thread1 = new Thread(() ->  {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        });

        Thread thread2 = new Thread(() ->  {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        });

        System.out.println(students.get(0));
        System.out.println(students.get(1));

        thread1.start();
        thread2.start();
    }

    public void printStudentWithThread2() {

        LOGGER.info("Запущен метод printStudentWithThread2()");

        List<Student> students = studentRepository.findAll();

        Thread thread1 = new Thread(() ->  {
            printStudent(students.subList(2,4));
        });

        Thread thread2 = new Thread(() ->  {
            printStudent(students.subList(4,6));
        });

        printStudent(students.subList(0,2));

        thread1.start();
        thread2.start();
    }

    private synchronized void printStudent(List<Student> students) {
        students.stream().forEach(System.out::println);
    }
}
