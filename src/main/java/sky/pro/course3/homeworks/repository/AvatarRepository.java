package sky.pro.course3.homeworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.course3.homeworks.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Avatar findAvatarByStudentId(Long studentId);
}
