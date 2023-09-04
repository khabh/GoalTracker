package com.goaltracker.auth.dto;

import com.goaltracker.user.validator.annotation.NoWhitespace;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDTO {
    @NoWhitespace
    @Size(min = 3, max = 11, message = "3에서 11 글자 사이로 입력해 주세요.")
    String username;

    @NotBlank(message = "이메일에는 공백이 포함될 수 없습니다.")
    @Email(message = "형식이 올바르지 않은 이메일입니다.")
    String email;

    @NoWhitespace
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "대소문자, 숫자, 특수문자를 포함한 8에서 20자리 문자열로 입력해 주세요.")
    String password;
}
