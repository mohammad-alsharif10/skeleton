package com.skeleton.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "token")
public class VerificationToken extends BaseModel<Long> {


    private String token;

    @OneToOne(fetch = LAZY)
    private User user;

    private Instant expiryDate;
}
