package com.goaltracker.user.util;

import com.goaltracker.user.domain.FollowRelation;
import com.goaltracker.user.domain.User;

public class FollowRelationConverter {
    private FollowRelationConverter() {}

    public static FollowRelation toFollowRelation(User followee, User follower) {
        FollowRelation followRelation = new FollowRelation();
        followRelation.setFollower(follower);
        followRelation.setFollowee(followee);

        return followRelation;
    }
}
