package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "유저명은 필수입니다.")
    @Size(max = 4, message = "유저명은 4글자 이내여야 합니다.")
    private String userName;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    private String password;

    public User toEntity() {
        return User.builder()
                .userName(this.userName)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
