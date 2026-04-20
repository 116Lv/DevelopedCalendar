package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String userName;
    private String email;

    public User toEntity() {
        return User.builder()
                .userName(this.userName)
                .email(this.email)
                .build();
    }
}
