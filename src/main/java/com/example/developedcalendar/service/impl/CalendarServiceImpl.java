package com.example.developedcalendar.service.impl;

import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;
import com.example.developedcalendar.repository.ScheduleRepository;
import com.example.developedcalendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        return new ScheduleResponseDto(scheduleRepository.save(dto.toEntity()));
    }
}
