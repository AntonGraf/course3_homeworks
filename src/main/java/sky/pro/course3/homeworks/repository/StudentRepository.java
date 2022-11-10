package sky.pro.course3.homeworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sky.pro.course3.homeworks.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository <Student, Long> {

    Collection<Student> findStudentsByAge(int age);
    Collection<Student> findStudentsByAgeBetween(int startAge, int endAge);
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
