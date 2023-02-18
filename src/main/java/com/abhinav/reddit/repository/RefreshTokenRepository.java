package com.abhinav.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.reddit.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<com.abhinav.reddit.model.RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}