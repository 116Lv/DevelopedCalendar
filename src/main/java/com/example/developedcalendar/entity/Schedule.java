package com.example.developedcalendar.entity;

import jakarta.persistence.*;
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
 * 일정(할 일) 정보를 관리하는 엔티티 클래스입니다.
 * 작성자(User)와는 다대일(N:1), 댓글(Comment)과는 일대다(1:N) 연관관계를 가집니다.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    /** 일정 고유 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    /** 일정 제목 (최대 10자, 필수값) */
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    /** 일정 상세 내용 (TEXT 타입으로 긴 문장 저장 가능) */
    @Column(columnDefinition = "TEXT")
    private String content;

    /** 일정을 작성한 유저 정보 (N:1 연관관계, 지연 로딩) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /** 일정 작성 일시 (JPA Auditing 자동 생성) */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeDate;

    /** 일정 최종 수정 일시 (JPA Auditing 자동 갱신) */
    @LastModifiedDate
    private LocalDateTime editDate;

    /** * 일정에 달린 댓글 목록 (1:N 양방향 연관관계)
     * 일정 삭제 시 관련 댓글도 모두 삭제되도록 영속성 전이(Cascade) 및 고아 객체 제거가 설정되어 있습니다.
     */
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * 일정의 제목과 내용을 수정합니다.
     * @param title 수정할 새로운 제목
     * @param content 수정할 새로운 내용
     * @return 수정된 Schedule 객체
     */
    public Schedule update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
