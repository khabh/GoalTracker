package com.goaltracker.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileEditViewDTO {
    private String nickname;
    private String email;
    private String introduction;
    private String interests;
}
