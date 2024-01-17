package com.green.greengram4.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
public class ErrorResponse { //에러가 발생했을때 리턴되는것
    private final String code;
    private final String message;
}
