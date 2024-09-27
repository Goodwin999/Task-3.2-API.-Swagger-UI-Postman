package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;

import java.util.Optional;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    @Override
    public Avatar saveAvatar(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    @Override
    public Optional<Avatar> getAvatarById(Long id) {
        return avatarRepository.findById(id);
    }

    @Override
    public void deleteAvatar(Long id) {
        avatarRepository.deleteById(id);
    }
}


