package com.example.developedcalendar.service;

import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;

import java.util.List;

public interface CalendarService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> getScheduleList();

    ScheduleResponseDto getSchedule(Long id);

    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);

    void deleteSchedule(Long id);
}
