package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;
    @Value("${avatar.upload.dir}")
    private String uploadDir;
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Avatar> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("studentId") Long studentId) throws IOException {

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Avatar avatar = new Avatar();
        avatar.setFilePath(file.getOriginalFilename());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());


        avatar = avatarService.saveAvatar(avatar);


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(uploadDir + File.separator + fileName);
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(file.getBytes());
        }

        return new ResponseEntity<>(avatar, HttpStatus.CREATED);
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
}