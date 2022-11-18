package sky.pro.course3.homeworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.course3.homeworks.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findFacultyByNameIgnoreCase(String name);

    Collection<Faculty> findFacultyByColorIgnoreCase(String color);
}
