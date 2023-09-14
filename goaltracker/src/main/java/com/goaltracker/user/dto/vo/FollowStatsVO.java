package com.goaltracker.user.dto.vo;

import lombok.Getter;

@Getter
public class FollowStatsVO {
    long followerCount;
    long followingCount;

    public FollowStatsVO(long followerCount, long followingCount) {
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }
}
