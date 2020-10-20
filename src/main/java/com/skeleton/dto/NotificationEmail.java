package com.skeleton.dto;

import com.skeleton.model.BaseModel;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEmail extends BaseModel<Long> {
    private String subject;
    private String recipient;
    private String body;
}
