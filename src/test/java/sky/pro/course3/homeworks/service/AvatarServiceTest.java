package sky.pro.course3.homeworks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.course3.homeworks.model.Avatar;
import sky.pro.course3.homeworks.model.Faculty;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.AvatarRepository;
import sky.pro.course3.homeworks.repository.StudentRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {


    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AvatarRepository avatarRepository;
    @InjectMocks
    private AvatarService out;

    private String avatarsInDir = "test-avatars\\in";

    @Test
    void uploadAvatar() throws IOException {

        Student testStudent = new Student();
        testStudent.setId(2);
        testStudent.setName("Драко");
        testStudent.setAge(19);

        Faculty testFaculty = new Faculty();
        testFaculty.setId(2);
        testFaculty.setName("Слизерин");
        testFaculty.setColor("зеленый");

        testStudent.setFaculty(testFaculty);


        when(studentRepository.findById(2L)).thenReturn(Optional.of(testStudent));
        when(avatarRepository.findAvatarByStudentId(2L)).thenReturn(new Avatar());

        File file = new File(avatarsInDir + "\\Драко.jpg");
        MultipartFile multipartFile = new MockMultipartFile("драко.jpg",
                file.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(file));

        out.uploadAvatar(2L, multipartFile);
    }
}