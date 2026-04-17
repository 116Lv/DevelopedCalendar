package com.example.developedcalendar.service;

import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;

public interface CalendarService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

}
