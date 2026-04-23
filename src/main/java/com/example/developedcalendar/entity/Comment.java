package com.example.developedcalendar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 일정에 달리는 댓글 정보를 관리하는 엔티티 클래스입니다.
 * 유저(User) 및 일정(Schedule)과 다대일(N:1) 연관관계를 가집니다.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    /** 댓글 고유 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    /** 댓글 내용 (최대 50자, 필수값) */
    @Column(nullable = false, length = 50)
    private String content;

    /** 댓글을 작성한 유저 정보 (N:1 연관관계, 지연 로딩) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /** 댓글이 달린 일정 정보 (N:1 연관관계, 지연 로딩) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    /** 댓글 작성 일시 (JPA Auditing을 통해 자동 생성) */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeDate;

    /** 댓글 최종 수정 일시 (JPA Auditing을 통해 자동 갱신) */
    @LastModifiedDate
    private LocalDateTime editDate;

    /**
     * 댓글의 내용을 수정합니다.
     * @param content 수정할 새로운 댓글 내용
     * @return 수정된 Comment 객체 (메서드 체이닝 가능)
     */
    public Comment update(String content) {
        this.content = content;
        return this;
    }
}
