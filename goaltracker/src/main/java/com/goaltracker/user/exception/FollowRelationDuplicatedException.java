package com.goaltracker.user.exception;

public class FollowRelationDuplicatedException extends RuntimeException {

    public FollowRelationDuplicatedException() {
        super("이미 팔로우 중인 사용자입니다.");
    }
}