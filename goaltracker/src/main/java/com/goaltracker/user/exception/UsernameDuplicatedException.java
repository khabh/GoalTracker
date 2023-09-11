package com.goaltracker.user.exception;

public class UsernameDuplicatedException extends RuntimeException {

    public UsernameDuplicatedException() {
        super("이미 사용 중인 이름입니다.");
    }
}