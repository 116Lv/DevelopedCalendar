package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 댓글 정보 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * Comment 엔티티의 데이터를 클라이언트에게 필요한 형태로 변환하여 전달합니다.
 */
@Getter
@Setter
public class CommentResponseDto {

    private Long commentId;             // 댓글 고유식별자
    private String content;             // 댓글 내용
    private Long writerId;              // 작성자 유저 고유식별자
    private Long scheduleId;            // 할일 고유식별자
    private LocalDateTime writeDate;    // 작성일
    private LocalDateTime editDate;     // 수정일

    /**
     * Comment 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param comment 변환할 댓글 엔티티 객체
     */
    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.writerId = comment.getUser().getUserId();
        this.scheduleId = comment.getSchedule().getScheduleId();
        this.writeDate = comment.getWriteDate();
        this.editDate = comment.getEditDate();
    }
}
