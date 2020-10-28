package com.skeleton;

import com.skeleton.config.CROSWebConfig;
import com.skeleton.dto.AuthenticationResponse;
import com.skeleton.dto.LoginDto;
import com.skeleton.dto.UserDto;
import com.skeleton.response.SingleResult;
import com.skeleton.service.AuthService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = ("http://localhost:4200"))
public class SkeletonApplication implements ApplicationRunner {


    private final AuthService authService;

    public SkeletonApplication(AuthService authService) {
        this.authService = authService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SkeletonApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<CROSWebConfig> corsFilterRegistration() {
        FilterRegistrationBean<CROSWebConfig> registrationBean =
                new FilterRegistrationBean<>(new CROSWebConfig());
        registrationBean.setName("CORS Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
////        authService.signup(UserDto.builder().username("mohammad1").password("mohammad").build());
//        ResponseEntity<SingleResult<Long, AuthenticationResponse>> login =
//                authService.login(LoginDto.builder().password("mohammad").username("mohammad1").build());
//        login.getBody();
//        System.out.println(login);
    }
}
