package com.muller.racha_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public boolean existsByEmail(String email);

    public Optional<User> findByEmail(String email);

}
