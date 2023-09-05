package com.goaltracker.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileEditDTO {
    private String username;
    private String introduction;
    private List<String> interests;
}
