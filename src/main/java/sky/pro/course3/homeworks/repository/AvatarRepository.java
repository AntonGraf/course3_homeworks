package sky.pro.course3.homeworks.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import sky.pro.course3.homeworks.model.Avatar;

public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Long> {

    Avatar findAvatarByStudentId(Long studentId);
}
