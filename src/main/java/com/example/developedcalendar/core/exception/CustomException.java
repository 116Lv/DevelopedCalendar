package com.example.developedcalendar.core.exception;

/**
 * 프로젝트에서 사용하는 커스텀 예외들을 한곳에 모아 관리합니다.
 */
public class CustomException {

    // 1. 권한 없음 예외
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    // 2. 데이터를 찾을 수 없음 예외
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

}
