package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Schedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 10, message = "할일 제목은 10글자 이내여야 합니다.")
    private String title;       // 할일 제목

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;     // 할일 내용

    @NotNull(message = "작성자 정보가 없습니다.")
    private Long writerId;      // 작성자 유저 고유식별자

    public Schedule toEntity() {
        return Schedule.builder()
                .title(this.title)
                .content(this.content)
                .writerId(this.writerId)
                .build();
    }
}
