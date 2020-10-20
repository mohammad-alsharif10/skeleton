package com.skeleton.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class LoginDto {


    private String username;


    private String password;

    protected LoginDto() {
    }


    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
