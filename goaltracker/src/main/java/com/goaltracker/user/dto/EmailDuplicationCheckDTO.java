package com.goaltracker.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDuplicationCheckDTO {
    boolean isDuplicated;

    public EmailDuplicationCheckDTO(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
