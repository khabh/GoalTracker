package com.goaltracker.user.exception.follow;

/**
 * 팔로잉 목록에 없는 사용자에 대해 언팔로우 요청을 보낼 경우 발생하는 예외입니다.
 */
public class FollowRelationNotFoundException extends InvalidFollowActionException {

    public FollowRelationNotFoundException() {
        super("팔로우 정보를 조회할 수 없습니다.");
    }
}