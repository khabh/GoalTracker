package com.goaltracker.user.dto;

import com.goaltracker.user.validator.annotation.NoWhitespace;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
    @NoWhitespace
    @Size(min = 3, max = 11, message = "3에서 11 글자 사이로 입력해 주세요.")
    private String username;

    @NotBlank(message = "이메일에는 공백이 포함될 수 없습니다.")
    @Email(message = "형식이 올바르지 않은 이메일입니다.")
    private String email;
}
