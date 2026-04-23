package com.example.developedcalendar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 시스템의 사용자 정보를 관리하는 핵심 엔티티 클래스입니다.
 * 일정(Schedule) 및 댓글(Comment)과 일대다(1:N) 양방향 연관관계를 가집니다.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    /** 유저 고유 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /** 유저 이름 (최대 4자) */
    @Size(max = 4)
    @Column(nullable = false, length = 4)
    private String userName;

    /** 유저 이메일 (로그인 ID로 사용되며, 중복이 허용되지 않음) */
    @Column(nullable = false, unique = true)
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    /** 암호화된 유저 비밀번호 (최소 8자 이상 저장 권장) */
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    private String password;

    /** 가입 일시 (JPA Auditing 자동 생성) */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime signUpDate;

    /** 정보 수정 일시 (JPA Auditing 자동 갱신) */
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    /**
     * 유저가 작성한 일정 목록 (1:N 양방향 연관관계)
     * 유저 삭제 시 작성한 모든 일정이 함께 삭제됩니다.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    /**
     * 유저가 작성한 댓글 목록 (1:N 양방향 연관관계)
     * 유저 삭제 시 작성한 모든 댓글이 함께 삭제됩니다.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * 유저의 이름과 이메일 정보를 수정합니다.
     * @param userName 수정할 새로운 이름
     * @param email 수정할 새로운 이메일
     * @return 수정된 User 객체
     */
    public User update(String userName, String email) {
        this.userName = userName;
        this.email = email;
        return this;
    }

}
