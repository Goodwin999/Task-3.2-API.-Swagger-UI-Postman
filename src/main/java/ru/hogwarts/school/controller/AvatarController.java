package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.AvatarDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;
    private final StudentService studentService;
    @Value("${avatar.upload.dir}")
    private String uploadDir;

    @Autowired
    public AvatarController(AvatarService avatarService, StudentService studentService, @Value("${avatar.upload.dir}") String uploadDir) {
        this.avatarService = avatarService;
        this.studentService = studentService;
        this.uploadDir = uploadDir;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarDTO> uploadAvatar(@RequestPart("file") MultipartFile file, @RequestParam("studentId") Long studentId) {
        File directory = new File(uploadDir);
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("Failed to create directory: " + uploadDir);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        try {
            Avatar avatar = new Avatar();
            avatar.setFilePath(file.getOriginalFilename());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());
            Student student = studentService.read(studentId);
            if (student == null) {
                return ResponseEntity.badRequest().body(null);
            }
            avatar.setStudent(student);
            avatar = avatarService.saveAvatar(avatar);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            System.out.println("Имя файла: " + fileName);
            Path filePath = Paths.get(uploadDir, fileName);
            System.out.println("Путь для сохранения файла: " + filePath.toString());

            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                System.out.println("Начало записи файла...");
                fos.write(file.getBytes());
                System.out.println("Файл успешно сохранен: " + filePath.toString());
            } catch (IOException e) {
                System.err.println("Ошибка при сохранении файла: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            AvatarDTO avatarDTO = new AvatarDTO();
            avatarDTO.setId(avatar.getId());
            avatarDTO.setFilePath(avatar.getFilePath());
            avatarDTO.setFileSize(avatar.getFileSize());
            avatarDTO.setMediaType(avatar.getMediaType());

            return new ResponseEntity<>(avatarDTO, HttpStatus.CREATED);

        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/from-db/{id}")
    public ResponseEntity<byte[]> getAvatarFromDatabase(@PathVariable Long id) {
        Optional<Avatar> avatarOptional = avatarService.getAvatarById(id);
        if (avatarOptional.isPresent()) {
            Avatar avatar = avatarOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                    .body(avatar.getData());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/from-disk/{fileName:.+}")
    public ResponseEntity<byte[]> getAvatarFromDisk(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir + File.separator + fileName);
        byte[] data = Files.readAllBytes(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable Long id) {
        avatarService.deleteAvatar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Avatar>> getAvatars(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(avatarService.getAvatars(page, size));
    }
}