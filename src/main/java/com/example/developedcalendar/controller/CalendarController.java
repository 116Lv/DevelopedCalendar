package com.example.developedcalendar.controller;

import com.example.developedcalendar.dto.*;
import com.example.developedcalendar.service.CalendarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 일정, 유저, 댓글 관리를 위한 API 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    /**
     * 새로운 일정을 생성합니다.
     * @param dto 일정 생성 요청 정보
     * @param loginUser 세션에 저장된 로그인 유저 정보
     * @return 생성된 일정 정보
     * @throws ResponseStatusException 로그인 정보가 없을 경우 401 예외 발생
     */
    @PostMapping("/schedules")
//  public ResponseEntity<ScheduleResponseDto> saveSchedule(@Valid @RequestBody ScheduleRequestDto dto, @SessionAttribute() Long loginUserId)
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@Valid @RequestBody ScheduleRequestDto dto, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.saveSchedule(loginUser.getUserId(), dto));
    }

    /**
     * 전체 일정 목록을 조회합니다.
     * @return 일정 응답 DTO 리스트
     */
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getScheduleList() {
        return ResponseEntity.ok(calendarService.getScheduleList());
    }

    /**
     * 특정 일정을 상세 조회합니다.
     * @param id 일정 고유 ID
     * @return 일정 상세 정보
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getSchedule(id));
    }

    /**
     * 기존 일정을 수정합니다.
     * @param id 수정할 일정 ID
     * @param dto 수정할 내용
     * @param loginUser 로그인 유저 정보
     * @return 수정된 일정 정보
     */
    @PatchMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto dto, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {


        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        return ResponseEntity.ok(calendarService.updateSchedule(loginUser.getUserId(), id, dto));
    }

    /**
     * 일정을 삭제합니다.
     * @param id 삭제할 일정 ID
     * @param loginUser 로그인 유저 정보
     * @return 반환값 없음 (204 No Content)
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        calendarService.deleteSchedule(loginUser.getUserId(), id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 신규 유저를 등록(회원가입)합니다.
     * @param dto 유저 생성 정보
     * @return 생성된 유저 정보
     */
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.saveUser(dto));
    }

    /**
     * 등록된 모든 유저 목록을 조회합니다.
     * @return 유저 리스트
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return ResponseEntity.ok(calendarService.getUserList());
    }

    /**
     * 유저 상세 정보를 조회합니다.
     * @param id 유저 고유 ID
     * @return 유저 상세 정보
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getUserInfo(id));
    }

    /**
     * 유저 정보를 업데이트합니다.
     * @param id 유저 ID
     * @param dto 수정할 유저 정보
     * @param loginUser 로그인 유저 정보
     * @return 수정된 유저 정보
     */
    @PatchMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUserInfo(@PathVariable Long id, @Valid @RequestBody UserRequestDto dto, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        return ResponseEntity.ok(calendarService.updateUserInfo(loginUser.getUserId(), id, dto));
    }

    /**
     * 유저를 탈퇴 처리(삭제)합니다.
     * @param id 유저 ID
     * @param loginUser 로그인 유저 정보
     * @return 반환값 없음
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        calendarService.deleteUser(loginUser.getUserId(), id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 유저 로그인을 수행하고 세션을 생성합니다.
     * @param dto 로그인 요청 정보 (이메일, 비밀번호)
     * @param request HttpServletRequest (세션 생성용)
     * @return 로그인 성공 메시지
     */
    @PostMapping("/users/login")
    public ResponseEntity<String> loginUSer(@Valid @RequestBody UserRequestDto dto, HttpServletRequest request) {
        if(!calendarService.authenticate(dto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 틀렸습니다.");
        }

        HttpSession session = request.getSession();

        UserResponseDto userInfo = calendarService.getUserByEmail(dto.getEmail());

        // 변경된 방식
        session.setAttribute("loginUser", new SessionUser(userInfo.getUserId(), userInfo.getEmail(), userInfo.getUserName()));

        return ResponseEntity.ok("로그인 성공");
    }

    //로그인 후 세션 및 정보 확인용
    @GetMapping("/users/check-session")
    public ResponseEntity<String> checkSession(@SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if (loginUser == null) {
            return ResponseEntity.ok("세션이 존재하지 않습니다.");
        }

        return ResponseEntity.ok("현재 로그인된 유저: " + loginUser.getUserName() + "님");
    }

    /**
     * 특정 일정에 댓글을 작성합니다.
     * @param scheduleId 일정 ID
     * @param dto 댓글 내용
     * @param loginUser 로그인 유저 정보
     * @return 생성된 댓글 정보
     */
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentResponseDto> saveComment(@PathVariable Long scheduleId, @Valid @RequestBody CommentRequestDto dto, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.saveComment(scheduleId, loginUser.getUserId(), dto));
    }

    /**
     * 특정 일정의 모든 댓글 목록을 조회합니다.
     * @param scheduleId 일정 ID
     * @return 댓글 리스트
     */
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(calendarService.getCommentList(scheduleId));
    }

    /**
     * 특정 댓글을 상세 조회합니다.
     * @param scheduleId 일정 ID
     * @param commentId 댓글 ID
     * @return 댓글 상세 정보
     */
    @GetMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long scheduleId, @PathVariable Long commentId) {
        return ResponseEntity.ok(calendarService.getComment(scheduleId, commentId));
    }

    /**
     * 댓글 내용을 수정합니다.
     * @param scheduleId 일정 ID
     * @param commentId 댓글 ID
     * @param dto 수정할 댓글 내용
     * @param loginUser 로그인 유저 정보
     * @return 수정된 댓글 정보
     */
    @PatchMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @Valid @RequestBody CommentRequestDto dto, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        return ResponseEntity.ok(calendarService.updateComment(scheduleId, commentId, loginUser.getUserId(), dto));
    }

    /**
     * 댓글을 삭제합니다.
     * @param scheduleId 일정 ID
     * @param commentId 댓글 ID
     * @param loginUser 로그인 유저 정보
     * @return 반환값 없음
     */
    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser) {

        if(loginUser == null || loginUser.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
        }

        calendarService.deleteComment(scheduleId, commentId, loginUser.getUserId());

        return ResponseEntity.noContent().build();
    }

}
