package com.skeleton;

import com.skeleton.config.CROSWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = ("http://localhost:4200"))
public class SkeletonApplication {

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

}
