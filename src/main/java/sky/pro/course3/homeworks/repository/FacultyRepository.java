package sky.pro.course3.homeworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.course3.homeworks.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findFacultyByColor(String color);

    Collection<Faculty> findFacultyByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
