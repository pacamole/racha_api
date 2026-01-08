package com.muller.racha_api.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muller.racha_api.dto.UpdateUserDTO;
import com.muller.racha_api.model.User;
import com.muller.racha_api.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("Não foi possível achar um usuário com o id: " + userId);
        });
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException("Não foi possível achar um usuário com o email: " + email);
        });
    }

    public User update(UUID userId, UpdateUserDTO dto) {
        User user = findUserById(userId);
        user.setName(dto.getName());

        return userRepository.save(user);
    }

    public void delete(UUID userId) {
        User user = findUserById(userId);

        userRepository.delete(user);
    }
}
