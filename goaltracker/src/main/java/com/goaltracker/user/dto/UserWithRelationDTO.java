package com.goaltracker.user.dto;

import com.goaltracker.user.constant.RelationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserWithRelationDTO {
    Long userId;
    String username;
    String introduction;
    RelationType relationType;
}
