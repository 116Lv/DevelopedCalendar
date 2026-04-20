package com.example.developedcalendar.controller;

import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;
import com.example.developedcalendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.saveSchedule(dto));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getScheduleList() {
        return ResponseEntity.ok(calendarService.getScheduleList());
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getSchedule(id));
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        return ResponseEntity.ok(calendarService.updateSchedule(id, dto));
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        calendarService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
