package com.skeleton.dto;

import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends BaseDto<Long> {


    private String username;

    private String password;


    private String email;

    private Instant created;

    private boolean enabled;
}
