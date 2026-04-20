package com.example.developedcalendar.service.impl;

import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;
import com.example.developedcalendar.repository.ScheduleRepository;
import com.example.developedcalendar.service.CalendarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        return new ScheduleResponseDto(scheduleRepository.save(dto.toEntity()));
    }

    @Override
    public List<ScheduleResponseDto> getScheduleList() {
        return scheduleRepository.findAllByOrderByScheduleIdAsc().stream().map(ScheduleResponseDto::new).toList();
    }

    @Override
    public ScheduleResponseDto getSchedule(Long id) {
        return scheduleRepository.findById(id).map(ScheduleResponseDto::new).orElseThrow(() -> new IllegalArgumentException("조회 실패"));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {
        return new ScheduleResponseDto(scheduleRepository.findById(id).orElseThrow().update(dto.getTitle(), dto.getContent()));
    }


}
