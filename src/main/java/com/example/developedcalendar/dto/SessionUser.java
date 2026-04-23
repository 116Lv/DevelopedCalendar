package com.example.developedcalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 세션에 저장할 유저의 핵심 정보를 담는 DTO 클래스입니다.
 * 인증된 유저의 정보를 세션에 유지함으로써 매번 DB를 조회하지 않고도 유저 정보를 식별할 수 있게 합니다.
 */
@Getter
@AllArgsConstructor
public class SessionUser {

    /** 유저 고유 식별자 (DB PK) */
    private Long userId;

    /** 유저 이메일 계정 */
    private String email;

    /** 유저 이름(닉네임) */
    private String userName;
}
