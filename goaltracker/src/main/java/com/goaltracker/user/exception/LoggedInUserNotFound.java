package com.goaltracker.user.exception;

public class LoggedInUserNotFound extends RuntimeException {
    public LoggedInUserNotFound() {
        super("사용자 인증 정보 오류 발생");
    }
}
