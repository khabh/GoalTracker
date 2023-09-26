package com.goaltracker.user.exception;

public class ConnectionTargetNotFoundException extends RuntimeException {
    public ConnectionTargetNotFoundException() {
        super("존재하지 않는 유저의 팔로잉, 팔로워 목록을 조회했습니다.");
    }
}
