package com.green.greengram4.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice //AOP용어 : AOP = 로그, 예외처리 할때 주로 사용
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.warn("handleException", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.warn("handleRestApiException", e);
        return handleExceptionInternal(e.getErrorCode());
    }

    // @Valid 어노테이션으로 넘어오는 에러 처리
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, CommonErrorCode.INVALID_PARAMETER);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode
            , String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(message == null
                        ? makeErrorResponse(errorCode)
                        : makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return makeErrorResponse(errorCode, errorCode.getMessage());
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    // @Valid 어노테이션으로 넘어오는 에러 처리 메세지를 보내기 위한 메소드
    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .valid(validationErrorList)
                .build();
    }
}
