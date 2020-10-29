package com.skeleton.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "security_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel<Long> {


    @Email
    @Column(name = "username", unique = true)
    private String username;


    @Column(name = "password")
    @JsonIgnore
    private String password;

    private Instant created;

    private boolean enabled;

    private String firstName;

    private String lastName;

    private String faculty;

    private String gender;

    private String university;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            targetEntity = UserRole.class,
            fetch = FetchType.EAGER
    )
    private List<UserRole> userRoles = new ArrayList<>();

}
