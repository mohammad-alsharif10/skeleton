package com.skeleton.service;

import com.skeleton.database.UserRepository;
import com.skeleton.database.VerificationTokenRepository;
import com.skeleton.dto.AuthenticationResponse;
import com.skeleton.dto.LoginDto;
import com.skeleton.dto.NotificationEmail;
import com.skeleton.dto.UserDto;
import com.skeleton.mapper.UserMapper;
import com.skeleton.model.User;
import com.skeleton.model.VerificationToken;
import com.skeleton.response.ResponseKeys;
import com.skeleton.response.SingleResult;
import com.skeleton.security.JwtProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
//@AllArgsConstructor
@Slf4j
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

    private final UserMapper userMapper;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
        this.userMapper = userMapper;
    }

    @SneakyThrows
    @Transactional
    public SingleResult<Long, UserDto> signup(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .build();
        User savedUser = userRepository.save(user);
        VerificationToken verificationToken = generateVerificationToken(user);
        mailService.sendMail(NotificationEmail.builder()
                .body("Activate your Account")
                .recipient(user.getEmail())
                .subject("Thank you for signing up to Spring Reddit, " +
                        "please click on the below url to activate your account : " +
                        "http://localhost:8080/api/auth/accountVerification/" + verificationToken.getToken())
                .build());
        return new SingleResult<>(false, ResponseKeys.SUCCESS_RESPONSE, "Registered", userMapper.toBaseDto(savedUser));
    }


    private VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .build();
        return verificationTokenRepository.save(verificationToken);
    }


    @SneakyThrows
    public SingleResult<Long, UserDto> verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isPresent()) {
            return new SingleResult<>(false, ResponseKeys.SUCCESS_RESPONSE, ResponseKeys.OK,
                    userMapper.toBaseDto(fetchUserAndEnable(verificationToken.get())));
        }
        return new SingleResult<>(true, ResponseKeys.EXCEPTION_RESPONSE, "Error", null);
    }

    private User fetchUserAndEnable(VerificationToken verificationToken) throws Exception {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found with name - " + username));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public SingleResult<Long, AuthenticationResponse> login(LoginDto loginDto) {
        SingleResult<Long, AuthenticationResponse> singleResult;
        Optional<String> token;
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            token = Optional.of(jwtProvider.generateToken(authenticate));
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .authenticationToken(token.orElse("Error"))
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                    .username(loginDto.getUsername())
                    .build();
            singleResult = new SingleResult<>(false, ResponseKeys.SUCCESS_RESPONSE, "Login Success", authenticationResponse);
        } catch (AuthenticationException arithmeticException) {
            log.info("Log in failed for user {}", loginDto.getUsername());
            singleResult = new SingleResult<>(true, ResponseKeys.EXCEPTION_RESPONSE, "Error", null);
        }
        return singleResult;
    }

}



