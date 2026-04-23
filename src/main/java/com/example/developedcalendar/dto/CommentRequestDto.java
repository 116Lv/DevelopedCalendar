package com.example.developedcalendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 생성 및 수정 요청 시 데이터를 전달받는 DTO 클래스입니다.
 */
@Getter
@Setter
public class CommentRequestDto {

    /**
     * 댓글 내용
     * 필수 입력값이며, 최대 50자까지 제한됩니다.
     */
    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 50, message = "내용은 50글자 이내여야 합니다.")
    private String content;

}
