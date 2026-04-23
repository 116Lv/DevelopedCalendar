package com.example.developedcalendar.core.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역에서 발생하는 예외를 핸들링하는 클래스입니다.
 * 각 예외 상황에 맞는 HTTP 상태 코드와 메시지를 클라이언트에게 반환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 인자 값이 전달되었을 때 발생하는 예외를 처리합니다.
     * @param e IllegalArgumentException 객체
     * @return 400 Bad Request와 에러 메시지
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * @Valid 어노테이션을 통한 유효성 검사 실패 시 발생하는 예외를 처리합니다.
     * 여러 에러 중 첫 번째 에러 메시지만 추출하여 반환합니다.
     * @param e MethodArgumentNotValidException 객체
     * @return 400 Bad Request와 검증 실패 메시지
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult().getFieldErrors().stream().findFirst().map(fieldError -> fieldError.getDefaultMessage()).orElse("");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    /**
     * 데이터베이스 제약 조건 위반 등 데이터 정합성 관련 예외를 처리합니다.
     * @param e DataIntegrityViolationException 객체
     * @return 409 Conflict와 안내 메시지
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("데이터 충돌이 발생했습니다. 요청 형식을 확인해주세요.");
    }

    /**
     * 요청한 리소스에 대한 권한이 없을 때 발생하는 예외를 처리합니다. (본인 확인 실패 등)
     * @param e UnauthorizedException 객체
     * @return 403 Forbidden과 권한 부족 메시지
     */
    @ExceptionHandler(CustomException.UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorized(CustomException.UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    /**
     * 요청한 데이터를 찾을 수 없을 때 발생하는 예외를 처리합니다.
     * @param e ResourceNotFoundException 객체
     * @return 404 Not Found와 에러 메시지
     */
    @ExceptionHandler(CustomException.ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(CustomException.ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
