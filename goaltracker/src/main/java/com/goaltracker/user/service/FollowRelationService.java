package com.goaltracker.user.service;

import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.vo.FollowStatsVO;

public interface FollowRelationService {
    FollowStatsVO getUserFollowStats(User user);
    boolean isFollowRelationExists(Long followeeId, Long followerId);
    void createFollowRelation(User followee, User follower);
}
