package com.example.developedcalendar.dto;

import com.example.developedcalendar.entity.Schedule;
import com.example.developedcalendar.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 일정 생성 및 수정 요청 시 데이터를 전달받는 DTO 클래스입니다.
 * 클라이언트로부터 받은 데이터를 검증하고 엔티티로 변환하는 기능을 포함합니다.
 */
@Getter
@Setter
public class ScheduleRequestDto {

    /**
     * 일정 제목
     * 필수 입력값이며, 최대 10자까지 제한됩니다.
     */
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 10, message = "할일 제목은 10글자 이내여야 합니다.")
    private String title;

    /**
     * 일정 내용
     * 필수 입력값입니다.
     */
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    /**
     * DTO 데이터를 기반으로 Schedule 엔티티 객체를 생성합니다.
     * @param user 일정을 생성하는 작성자(User) 엔티티
     * @return 빌더 패턴이 적용된 Schedule 엔티티 객체
     */
    public Schedule toEntity(User user) {
        return Schedule.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
    }
}
