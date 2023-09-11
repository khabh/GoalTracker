package com.goaltracker.user.exception;

public class EmailDuplicatedException extends RuntimeException {

    public EmailDuplicatedException() {
        super("이미 가입된 이메일입니다.");
    }
}