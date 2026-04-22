package com.example.developedcalendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 50, message = "내용은 50글자 이내여야 합니다.")
    private String content;

}
