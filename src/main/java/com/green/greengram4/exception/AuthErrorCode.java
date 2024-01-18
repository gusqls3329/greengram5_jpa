package com.green.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter // ErrorCode interface에 있는 getHttpStatus, getMessage를 오버라이딩 함
// (name은 enum이 메소드를 가지고 있어서 하지않음)
@RequiredArgsConstructor
public enum AuthErrorCode implements  ErrorCode{ //enum :
    NOT_EXIST_USER_ID(HttpStatus.NOT_FOUND,"아이디가 존재하지 않습니다"),
    VALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요"),
    NEED_SIGNIN(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다"),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "refresh-token이 없습니다.");
    // enum은 기본적으로 name이라는 메소드를 가지고 있고 name을 가지고 있다(그래서 NOT_FOUND_REFRESH_TOKEN 를 리턴해준다)
    private final HttpStatus httpStatus;
    private final String message;
    /*
    @RequiredArgsConstructor =
    AuthErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }
     */
}
