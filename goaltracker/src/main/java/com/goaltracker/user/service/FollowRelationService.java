package com.goaltracker.user.service;

import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.vo.FollowStatsVO;

import java.util.List;
import java.util.Set;

public interface FollowRelationService {
    FollowStatsVO getUserFollowStats(User user);

    boolean isFollowRelationExists(Long followeeId, Long followerId);

    void createFollowRelation(User followee, User follower);

    void deleteFollowRelation(Long followeeId, Long followerId);

    List<User> getFollowersOfUser(User user);
    Set<Long> getUserFollowingIds(User user);
}
