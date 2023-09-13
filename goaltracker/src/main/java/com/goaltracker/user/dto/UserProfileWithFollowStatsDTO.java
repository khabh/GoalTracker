package com.goaltracker.user.dto;

import com.goaltracker.user.constant.RelationType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserProfileWithFollowStatsDTO {
    String username;
    int followerCount;
    int followingCount;
    String description;
    List<String> interests;
    RelationType relationType;
}
