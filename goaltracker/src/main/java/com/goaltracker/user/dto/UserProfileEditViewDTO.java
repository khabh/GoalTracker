package com.goaltracker.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileEditViewDTO {
    private String username;
    private String email;
    private String introduction;
    private String interests;
}
