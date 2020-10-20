package com.skeleton.model;

import lombok.*;

import javax.persistence.Entity;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken extends BaseModel<Long> {
    private String token;
    private Instant createdDate;
}
