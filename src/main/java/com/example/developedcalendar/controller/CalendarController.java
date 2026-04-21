package com.example.developedcalendar.controller;

import com.example.developedcalendar.dto.ScheduleRequestDto;
import com.example.developedcalendar.dto.ScheduleResponseDto;
import com.example.developedcalendar.dto.UserRequestDto;
import com.example.developedcalendar.dto.UserResponseDto;
import com.example.developedcalendar.service.CalendarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@Valid @RequestBody ScheduleRequestDto dto) {

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
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto dto) {
        return ResponseEntity.ok(calendarService.updateSchedule(id, dto));
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        calendarService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.saveUser(dto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return ResponseEntity.ok(calendarService.getUserList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getUserInfo(id));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUserInfo(@PathVariable Long id, @Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(calendarService.updateUserInfo(id, dto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        calendarService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> loginUSer(@Valid @RequestBody UserRequestDto dto, HttpServletRequest request) {
        if(!calendarService.authenticate(dto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 틀렸습니다.");
        }

        HttpSession session = request.getSession();

        UserResponseDto userInfo = calendarService.getUserByEmailAndPassword(dto);

        session.setAttribute("loginUserId", userInfo.getUserId());
        session.setAttribute("loginEmailAddress", userInfo.getEmail());
        session.setAttribute("loginUserName", userInfo.getUserName());

        return ResponseEntity.ok("로그인 성공");

    }

    //로그인 후 세션 및 정보 확인용
    @GetMapping("/users/check-session")
    public ResponseEntity<String> checkSession(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return ResponseEntity.ok("세션이 존재하지 않습니다.");
        }

        String userName = (String) session.getAttribute("loginUserName");


        if (userName == null) {
            return ResponseEntity.ok("세션은 있지만 유저 정보가 없습니다.");
        }

        return ResponseEntity.ok("현재 로그인된 유저: " + userName + "님");
    }

}
