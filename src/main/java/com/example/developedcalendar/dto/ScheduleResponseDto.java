package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDto {

    private Long scheduleId;            // 할일 고유식별자
    private String title;               // 할일 제목
    private String content;             // 할일 내용
    private Long writerId;              // 작성자 유저 고유식별자
    private LocalDateTime writeDate;    // 작성일
    private LocalDateTime editDate;     // 수정일

    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.writerId = schedule.getWriterId();
        this.writeDate = schedule.getWriteDate();
        this.editDate = schedule.getEditDate();
    }
}
