package com.goaltracker.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileChangeDTO {
    @Size(max = 10, message = "닉네임은 2에서 10자 이내로 입력해 주세요.")
    private String nickname;

    @Size(max = 50, message = "자기소개는 50자 이내로 입력해 주세요.")
    private String introduction;

    @Size(max = 10, message = "관심 태그는 최대 10개까지 입력할 수 있습니다.")
    private List<String> interests;
}
