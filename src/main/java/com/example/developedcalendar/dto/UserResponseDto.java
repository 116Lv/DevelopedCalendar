package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 유저 정보 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * User 엔티티의 정보 중 비밀번호를 제외한 공개 가능한 정보만을 담아 클라이언트에게 전달합니다.
 */
@Getter
@Setter
public class UserResponseDto {

    private Long userId;                    // 유저 고유식별자
    private String userName;                // 유저 이름
    private String email;                   // 이메일
    private LocalDateTime signUpDate;       // 가입일
    private LocalDateTime modifiedDate;     // 수정일

    /**
     * User 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param user 변환할 유저 엔티티 객체
     */
    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.signUpDate = user.getSignUpDate();
        this.modifiedDate = user.getModifiedDate();
    }
}
