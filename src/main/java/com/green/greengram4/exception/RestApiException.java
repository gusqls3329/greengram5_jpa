package com.green.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends  RuntimeException{ // 컨파일 exception : 실행되지가 않음..
    private final ErrorCode errorCode;
}
