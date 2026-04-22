package com.example.developedcalendar.service;

import com.example.developedcalendar.dto.*;
import jakarta.validation.Valid;

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

    UserResponseDto getUserByEmail(String email);

    CommentResponseDto saveComment(Long id, Long loginUserId, @Valid CommentRequestDto dto);

    List<CommentResponseDto> getCommentList(Long scheduleId);

    CommentResponseDto getComment(Long scheduleId, Long commentId);

    CommentResponseDto updateComment(Long scheduleId, Long commentId, Long userId, @Valid CommentRequestDto dto);

    void deleteComment(Long scheduleId, Long commentId, Long userId);
}
