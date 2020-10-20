package com.skeleton.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;


@Entity
@Table(name = "security_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel<Long> {


    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;


    @Column(name = "password")
    @JsonIgnore
    private String password;

    private Instant created;

    private boolean enabled;

}
