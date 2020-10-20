package com.skeleton.database;

import com.skeleton.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
