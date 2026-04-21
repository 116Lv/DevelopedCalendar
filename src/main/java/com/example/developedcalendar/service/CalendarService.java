package com.example.developedcalendar.service;

import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;
import com.example.developedcalendar.dto.UserRequestDto;
import com.example.developedcalendar.dto.UserResponseDto;

import java.util.List;

public interface CalendarService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> getScheduleList();

    ScheduleResponseDto getSchedule(Long id);

    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);

    void deleteSchedule(Long id);

    UserResponseDto saveUser(UserRequestDto dto);

    List<UserResponseDto> getUserList();

    UserResponseDto getUserInfo(Long id);

    UserResponseDto updateUserInfo(Long id, UserRequestDto dto);

    void deleteUser(Long id);

    boolean authenticate(UserRequestDto dto);
}
