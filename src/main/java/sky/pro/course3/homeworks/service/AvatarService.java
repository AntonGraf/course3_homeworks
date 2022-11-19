package sky.pro.course3.homeworks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.course3.homeworks.model.Avatar;
import sky.pro.course3.homeworks.model.Student;
import sky.pro.course3.homeworks.repository.AvatarRepository;
import sky.pro.course3.homeworks.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AvatarService.class);

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {

        LOGGER.info("Запущен метод uploadAvatar");
        Student student = studentRepository.findById(studentId).get();
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        LOGGER.debug("Добавляется avatar " + avatar);

        avatarRepository.save(avatar);
    }
    private String getExtensions(String fileName) {
        LOGGER.info("Запускается метод getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long studentId) {
        LOGGER.info("Запускается метод findAvatar");
        Avatar avatar = avatarRepository.findAvatarByStudentId(studentId);

        if (avatar == null) {
            avatar = new Avatar();
            LOGGER.warn("Аватар для studentId" + studentId + " не найден. Создается новый");
        }

        return avatar;
    }

    public Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        LOGGER.info("Запускается метод getAllAvatars");

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        LOGGER.debug("Запрос страницы номер " + pageNumber);
        LOGGER.debug("Количество элементов на странице " + pageSize);

        return avatarRepository.findAll(pageRequest).getContent();
    }
}
