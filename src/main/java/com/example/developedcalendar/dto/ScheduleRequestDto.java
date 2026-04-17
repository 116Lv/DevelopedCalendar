package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    private String title;       // 할일 제목
    private String content;     // 할일 내용
    private String writerName;  // 작성자명

    public Schedule toEntity() {
        return Schedule.builder()
                .title(this.title)
                .content(this.content)
                .writerName(this.writerName)
                .build();
    }
}
