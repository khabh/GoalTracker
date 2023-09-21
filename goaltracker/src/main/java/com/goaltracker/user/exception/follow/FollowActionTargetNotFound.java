package com.goaltracker.user.exception.follow;

/**
 * 팔로우 또는 언팔로우 시 요청 대상을 찾을 수 없을 경우 발생하는 예외입니다.
 */
public class FollowActionTargetNotFound extends InvalidFollowActionException {
    public FollowActionTargetNotFound() {
        super("요청 대상을 찾을 수 없습니다.");
    }
}
