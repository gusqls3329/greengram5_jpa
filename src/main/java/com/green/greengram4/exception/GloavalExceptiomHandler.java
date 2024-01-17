package com.green.greengram4.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice //AOP용어 : AOP = 로그, 예외처리 할때 주로 사용
public class GloavalExceptiomHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e){
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(Exception.class) //대부분의 Exception을 이클래스가 잡음
    public ResponseEntity<Object> handleException(Exception e){
        log.warn("handleException",e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestApiException.class) //Customizing된 에러코드
    public ResponseEntity<Object> handleRestApiException(RestApiException e){
        log.warn("handleException",e);
        return handleExceptionInternal(e.getErrorCode());
    }


    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(message == null
                        ? makeErrorResponse(errorCode)
                        : makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode){ //원래 저장한 에러코드의 메시지를 보냄
        return makeErrorResponse(errorCode, errorCode.getMessage());
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message){ //에러코드의 메시지를 바꿔서 보냄
        return ErrorResponse.builder()
                .code(errorCode.name()) // code = 제목
                .message(message)
                .build();
    }
}
