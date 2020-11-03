package com.skeleton.security;


import com.skeleton.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private final String ROLES_KEY = "roles";
    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @SneakyThrows
    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/keystore.jks");
            keyStore.load(resourceAsStream, "123456".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new Exception("Exception occurred while loading keystore", e);
        }

    }

    public String generateToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ROLES_KEY, roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    @SneakyThrows
    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("keystore", "123456".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new Exception("Exception occurred while retrieving public key from keystore", e);
        }
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    @SneakyThrows
    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("keystore").getPublicKey();
        } catch (KeyStoreException e) {
            throw new Exception("Exception occurred while " +
                    "retrieving public key from keystore", e);
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
