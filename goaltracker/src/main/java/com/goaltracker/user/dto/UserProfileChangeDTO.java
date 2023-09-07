package com.goaltracker.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileChangeDTO {
    private String introduction;

    @Size(max = 10)
    private List<String> interests;
}
