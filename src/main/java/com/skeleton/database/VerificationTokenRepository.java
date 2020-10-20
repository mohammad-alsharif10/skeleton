package com.skeleton.database;

import com.skeleton.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends BaseRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
