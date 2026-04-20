package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {

    private Long userId;
    private String userName;
    private String email;
    private LocalDateTime signUpDate;
    private LocalDateTime modifiedDate;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.signUpDate = user.getSignUpDate();
        this.modifiedDate = user.getModifiedDate();
    }
}
