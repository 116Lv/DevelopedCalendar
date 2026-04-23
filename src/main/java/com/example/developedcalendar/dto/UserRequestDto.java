package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 등록(회원가입) 및 정보 수정 요청 시 데이터를 전달받는 DTO 클래스입니다.
 * 입력된 유저 정보의 유효성을 검증하고 엔티티로 변환하는 기능을 수행합니다.
 */
@Getter
@Setter
public class UserRequestDto {

    /**
     * 유저의 이름 또는 닉네임
     * 최대 4자까지 제한됩니다.
     */
    @Size(max = 4, message = "유저명은 4글자 이내여야 합니다.")
    private String userName;

    /**
     * 유저의 이메일 계정
     * 필수 입력값이며, 유효한 이메일 형식(@ 포함 등)이어야 합니다.
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    /**
     * 유저의 비밀번호
     * 필수 입력값이며, 보안을 위해 최소 8자 이상으로 설정해야 합니다.
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    private String password;

    /**
     * 암호화된 비밀번호를 받아 User 엔티티 객체를 생성합니다.
     * @param encodedPassword PasswordEncoder에 의해 암호화된 비밀번호 문자열
     * @return 빌더 패턴이 적용된 User 엔티티 객체
     */
    public User toEntity(String encodedPassword) {
        return User.builder()
                .userName(this.userName)
                .email(this.email)
                .password(encodedPassword)
                .build();
    }
}
