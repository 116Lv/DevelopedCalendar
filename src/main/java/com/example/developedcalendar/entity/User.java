package com.example.developedcalendar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String email;

    private String password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime signUpDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public User update(String userName, String email) {
        this.userName = userName;
        this.email = email;
        return this;
    }

}
