package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    private String title;       // 할일 제목
    private String content;     // 할일 내용
    private Long writerId;      // 작성자 유저 고유식별자

    public Schedule toEntity() {
        return Schedule.builder()
                .title(this.title)
                .content(this.content)
                .writerId(this.writerId)
                .build();
    }
}
