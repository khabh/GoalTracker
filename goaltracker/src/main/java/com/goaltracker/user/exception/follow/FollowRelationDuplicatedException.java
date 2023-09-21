package com.goaltracker.user.exception.follow;

/**
 * 이미 팔로우 중인 대상에 대해 팔로우 요청을 중복으로 할 경우 발생하는 예외입니다.
 */
public class FollowRelationDuplicatedException extends InvalidFollowActionException {

    public FollowRelationDuplicatedException() {
        super("이미 팔로우 중인 사용자입니다.");
    }
}