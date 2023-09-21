package com.goaltracker.user.exception.follow;

/**
 * 팔로우할 대상과 팔로우 요청 사용자가 같을 경우 발생하는 예외입니다.
 */
public class AttemptedSelfFollowException extends InvalidFollowActionException {
    public AttemptedSelfFollowException() {
        super("자기 자신에게 팔로우 요청을 보낼 수 없습니다.");
    }
}
