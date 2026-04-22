package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {

    private Long commentId;             // 댓글 고유식별자
    private String content;             // 댓글 내용
    private Long writerId;              // 작성자 유저 고유식별자
    private Long scheduleId;            // 할일 고유식별자
    private LocalDateTime writeDate;    // 작성일
    private LocalDateTime editDate;     // 수정일

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.writerId = comment.getWriterId();
        this.scheduleId = comment.getScheduleId();
        this.writeDate = comment.getWriteDate();
        this.editDate = comment.getEditDate();
    }
}
