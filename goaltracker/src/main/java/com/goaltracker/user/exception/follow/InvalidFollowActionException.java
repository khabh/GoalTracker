package com.goaltracker.user.exception.follow;

public class InvalidFollowActionException extends RuntimeException {
    protected InvalidFollowActionException(String errorMessage) {
        super(errorMessage);
    }
}
