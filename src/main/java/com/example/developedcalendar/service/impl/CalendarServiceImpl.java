package com.example.developedcalendar.service.impl;

import com.example.developedcalendar.common.PasswordEncoder;
import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;
import com.example.developedcalendar.dto.UserRequestDto;
import com.example.developedcalendar.dto.UserResponseDto;
import com.example.developedcalendar.repository.ScheduleRepository;
import com.example.developedcalendar.repository.UserRepository;
import com.example.developedcalendar.service.CalendarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final ScheduleRepository scheduleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
        return scheduleRepository.findById(id).map(ScheduleResponseDto::new).orElseThrow(() -> new IllegalArgumentException("해당 ID값의 일정을 찾을수 없습니다."));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {
        return new ScheduleResponseDto(scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID값의 일정을 찾을수 없습니다.")).update(dto.getTitle(), dto.getContent()));
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        return new UserResponseDto(userRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword()))));
    }

    @Override
    public List<UserResponseDto> getUserList() {
        return userRepository.findAllByOrderByUserIdDesc().stream().map(UserResponseDto::new).toList();
    }

    @Override
    public UserResponseDto getUserInfo(Long id) {
        return userRepository.findById(id).map(UserResponseDto::new).orElseThrow(() -> new IllegalArgumentException("해당 Id값의 유저는 존재하지 않습니다."));
    }

    @Override
    @Transactional
    public UserResponseDto updateUserInfo(Long id, UserRequestDto dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        return new UserResponseDto(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 Id값의 유저는 존재하지 않습니다.")).update(dto.getUserName(), dto.getEmail()));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean authenticate(UserRequestDto dto) {
        return userRepository.findByEmail(dto.getEmail()).map(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword())).orElse(false);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponseDto::new).orElseThrow(() -> new IllegalArgumentException("입력한 정보와 일치한 유저는 존재하지 않습니다."));
    }


}
