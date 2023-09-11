package com.goaltracker.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameDuplicationCheckDTO {
    @JsonProperty("isDuplicated")
    boolean isDuplicated;

    public UsernameDuplicationCheckDTO(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
