package com.skeleton.service;

import com.skeleton.database.RoleRepository;
import com.skeleton.database.UserRepository;
import com.skeleton.database.UserRoleRepository;
import com.skeleton.database.VerificationTokenRepository;
import com.skeleton.dto.AuthenticationResponse;
import com.skeleton.dto.LoginDto;
import com.skeleton.dto.UserDto;
import com.skeleton.mapper.UserMapper;
import com.skeleton.model.Role;
import com.skeleton.model.User;
import com.skeleton.model.UserRole;
import com.skeleton.model.VerificationToken;
import com.skeleton.response.ResponseKeys;
import com.skeleton.response.SingleResult;
import com.skeleton.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;


    @SneakyThrows
    @Transactional
    public ResponseEntity<SingleResult<Long, UserDto>> signup(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .created(Instant.now())
                .enabled(true)
                .build();
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new SingleResult<>(true, 406,
                    "Email Already Exist", null), HttpStatus.NOT_ACCEPTABLE);

        }
        User savedUser = saveUser(user);
//        VerificationToken verificationToken = generateVerificationToken(user);
//        mailService.sendMail(NotificationEmail.builder()
//                .body("Activate your Account")
//                .recipient(user.getUsername())
//                .subject("Thank you for signing up to Spring Reddit, " +
//                        "please click on the below url to activate your account : " +
//                        "http://localhost:8080/api/auth/accountVerification/" + verificationToken.getToken())
//                .build());
        return new ResponseEntity<>(new SingleResult<>(false, 200,
                "Registered", userMapper.toBaseDto(savedUser)), HttpStatus.NOT_ACCEPTABLE);
    }

    private User saveUser(User user) {
        user = userRepository.save(user);
        Role roleStudent = roleRepository.roleStudent();
        Role roleBroker = roleRepository.roleBroker();
        userRoleRepository.saveAll(Arrays.asList(new UserRole(user, roleStudent), new UserRole(user, roleBroker)));
        return user;
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

    public ResponseEntity<SingleResult<Long, AuthenticationResponse>> login(LoginDto loginDto) {
        SingleResult<Long, AuthenticationResponse> singleResult;
        Optional<String> token;

        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());
        if (user.isEmpty()) {
            singleResult = new SingleResult<>(
                    true,
                    404,
                    "Email Does Not Exist",
                    null);
            return new ResponseEntity<>(singleResult, HttpStatus.NOT_FOUND);
        }
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            token = Optional.of(jwtProvider.generateToken(user.get().getUsername(),
                    user.get()
                            .getUserRoles()
                            .stream()
                            .map(UserRole::getRole)
                            .collect(Collectors.toList())));
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .authenticationToken(token.orElse("Error"))
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                    .username(loginDto.getUsername())
                    .build();
            singleResult = new SingleResult<>(false, 200, "Login Success", authenticationResponse);
        } catch (AuthenticationException arithmeticException) {
            log.info("Log in failed for user {}", loginDto.getUsername());
            singleResult = new SingleResult<>(
                    true,
                    404,
                    "Invalid Login",
                    null);
        }
        return new ResponseEntity<>(singleResult, HttpStatus.OK);
    }


}



