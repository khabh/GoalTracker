package com.goaltracker.auth.dto;

import com.goaltracker.user.validator.annotation.NoWhitespace;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialPasswordDTO {

    @NoWhitespace
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "대소문자, 숫자, 특수문자를 포함한 8에서 20자리 문자열로 입력해 주세요.")
    private String password;
}
