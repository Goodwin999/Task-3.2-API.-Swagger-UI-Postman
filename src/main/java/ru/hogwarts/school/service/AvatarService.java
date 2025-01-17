package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.hogwarts.school.model.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarService {
    Optional<Avatar> getAvatarById(Long id);

    Avatar saveAvatar(Avatar avatar);

    void deleteAvatar(Long id);

    Page<Avatar> getAvatars(Pageable pageable);
}
