package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarService {
    Optional<Avatar> getAvatarById(Long id);

    Avatar saveAvatar(Avatar avatar);

    void deleteAvatar(Long id);

    List<Avatar> getAvatars(int page, int size);
}
