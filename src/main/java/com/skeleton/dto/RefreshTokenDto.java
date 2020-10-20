package com.skeleton.dto;

import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDto extends BaseDto<Long> {

    private String token;
    private Instant createdDate;
}
