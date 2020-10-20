package com.skeleton.controller;


import com.skeleton.dto.AuthenticationResponse;
import com.skeleton.dto.LoginDto;
import com.skeleton.dto.UserDto;
import com.skeleton.response.SingleResult;
import com.skeleton.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public SingleResult<Long, UserDto> signup(@RequestBody UserDto registerRequestDto) {
        return authService.signup(registerRequestDto);

    }

    @RequestMapping(value = "accountVerification/{token}", method = RequestMethod.GET)
    public SingleResult<Long, UserDto> verifyAccount(@PathVariable String token) {
        return authService.verifyAccount(token);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SingleResult<Long, AuthenticationResponse> login(@RequestBody LoginDto loginRequest) {
        return authService.login(loginRequest);
    }

}

