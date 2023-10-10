package com.goaltracker.user.dto;

import com.goaltracker.user.constant.RelationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class UserProfileWithFollowStatsDTO {
    Long userId;
    String username;
    String nickname;
    long followerCount;
    long followingCount;
    String introduction;
    List<String> interests;
    RelationType relationType;
}
