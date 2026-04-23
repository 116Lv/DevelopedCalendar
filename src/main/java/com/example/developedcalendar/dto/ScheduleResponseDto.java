package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 일정 정보 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * Schedule 엔티티를 클라이언트에 필요한 정보만 담은 응답 객체로 변환합니다.
 */
@Getter
@Setter
public class ScheduleResponseDto {

    private Long scheduleId;            // 할일 고유식별자
    private String title;               // 할일 제목
    private String content;             // 할일 내용
    private Long writerId;              // 작성자 유저 고유식별자
    private LocalDateTime writeDate;    // 작성일
    private LocalDateTime editDate;     // 수정일

    /**
     * Schedule 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param schedule 변환할 일정 엔티티 객체
     */
    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.writerId = schedule.getUser().getUserId();
        this.writeDate = schedule.getWriteDate();
        this.editDate = schedule.getEditDate();
    }
}
