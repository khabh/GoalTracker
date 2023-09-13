package com.goaltracker.user.dto.vo;

import lombok.Getter;

@Getter
public class FollowStatsVO {
    int followerCount;
    int followingCount;

    public FollowStatsVO(int followerCount, int followingCount) {
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }
}
