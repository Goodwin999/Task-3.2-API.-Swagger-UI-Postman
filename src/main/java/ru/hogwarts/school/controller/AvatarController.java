package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private AvatarService avatarService;

    @PostMapping
    public ResponseEntity<Avatar> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("studentId") Long studentId) throws IOException {
        Avatar avatar = new Avatar();
        avatar.setFilePath(file.getOriginalFilename());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        Student student = new Student();
        student.setId(studentId);
        avatar.setStudent(student);

        Avatar savedAvatar = avatarService.saveAvatar(avatar);
        return new ResponseEntity<>(savedAvatar, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avatar> getAvatar(@PathVariable Long id) {
        Optional<Avatar> avatar = avatarService.getAvatarById(id);
        return avatar.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable Long id) {
        avatarService.deleteAvatar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}