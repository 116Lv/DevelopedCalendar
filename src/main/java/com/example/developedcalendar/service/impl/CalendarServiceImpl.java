package com.example.developedcalendar.service.impl;

import com.example.developedcalendar.core.config.PasswordEncoder;
import com.example.developedcalendar.core.exception.CustomException;
import com.example.developedcalendar.dto.*;
import com.example.developedcalendar.entity.Comment;
import com.example.developedcalendar.entity.Schedule;
import com.example.developedcalendar.entity.User;
import com.example.developedcalendar.repository.CommentRepository;
import com.example.developedcalendar.repository.ScheduleRepository;
import com.example.developedcalendar.repository.UserRepository;
import com.example.developedcalendar.service.CalendarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 캘린더 시스템의 핵심 비즈니스 로직을 구현하는 서비스 클래스입니다.
 * 일정, 유저, 댓글에 대한 생성, 조회, 수정, 삭제 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 일정을 저장합니다.
     * @param userId 일정을 작성하는 유저의 고유 ID
     * @param dto 일정 생성을 위한 요청 데이터
     * @return 저장된 일정 정보 (DTO)
     * @throws CustomException.ResourceNotFoundException 유저를 찾을 수 없는 경우 발생
     */
    @Override
    @Transactional
    public ScheduleResponseDto saveSchedule(Long userId, ScheduleRequestDto dto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException.ResourceNotFoundException("존재하지 않는 유저입니다."));

        return new ScheduleResponseDto(scheduleRepository.save(dto.toEntity(user)));
    }

    /**
     * 전체 일정 목록을 생성일 순으로 조회합니다.
     * @return 일정 응답 DTO 리스트
     */
    @Override
    public List<ScheduleResponseDto> getScheduleList() {
        return scheduleRepository.findAllByOrderByScheduleIdAsc().stream().map(ScheduleResponseDto::new).toList();
    }

    /**
     * ID를 통해 특정 일정을 상세 조회합니다.
     * @param id 일정 고유 ID
     * @return 조회된 일정 정보
     * @throws CustomException.ResourceNotFoundException 해당 ID의 일정이 없을 경우 발생
     */
    @Override
    public ScheduleResponseDto getSchedule(Long id) {
        return scheduleRepository.findById(id).map(ScheduleResponseDto::new).orElseThrow(() -> new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다."));
    }

    /**
     * 기존 일정을 수정합니다. 작성자 본인 확인 절차를 포함합니다.
     * @param userId 수정을 요청한 유저의 ID
     * @param id 수정할 일정의 ID
     * @param dto 수정할 내용이 담긴 DTO
     * @return 수정 완료된 일정 정보
     * @throws CustomException.ResourceNotFoundException 일정이 존재하지 않을 경우 발생
     * @throws CustomException.UnauthorizedException 작성자 본인이 아닐 경우 발생
     */
    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long userId, Long id, ScheduleRequestDto dto) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다."));

        if(!schedule.getUser().getUserId().equals(userId)) {
            throw new CustomException.UnauthorizedException("작성하신 본인만 접근 가능합니다.");
        }

        return new ScheduleResponseDto(schedule.update(dto.getTitle(), dto.getContent()));
    }

    /**
     * 일정을 삭제합니다. 작성자 본인 확인 절차를 포함합니다.
     * @param userId 삭제를 요청한 유저의 ID
     * @param id 삭제할 일정의 ID
     * @throws CustomException.ResourceNotFoundException 일정이 존재하지 않을 경우 발생
     * @throws CustomException.UnauthorizedException 작성자 본인이 아닐 경우 발생
     */
    @Override
    @Transactional
    public void deleteSchedule(Long userId, Long id) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다."));

        if(!schedule.getUser().getUserId().equals(userId)) {
            throw new CustomException.UnauthorizedException("작성하신 본인만 접근 가능합니다.");
        }

        scheduleRepository.deleteById(id);
    }

    /**
     * 신규 유저를 저장합니다. 이메일 중복 체크 및 비밀번호 암호화를 수행합니다.
     * @param dto 유저 가입 정보
     * @return 저장된 유저 정보
     * @throws IllegalArgumentException 이미 존재하는 이메일일 경우 발생
     */
    @Override
    @Transactional
    public UserResponseDto saveUser(UserRequestDto dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        return new UserResponseDto(userRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword()))));
    }

    /**
     * 모든 유저 목록을 최신순으로 조회합니다.
     * @return 유저 응답 DTO 리스트
     */
    @Override
    public List<UserResponseDto> getUserList() {
        return userRepository.findAllByOrderByUserIdDesc().stream().map(UserResponseDto::new).toList();
    }

    /**
     * 특정 유저의 상세 정보를 조회합니다.
     * @param id 유저 ID
     * @return 유저 상세 정보
     * @throws CustomException.ResourceNotFoundException 유저를 찾을 수 없는 경우 발생
     */
    @Override
    public UserResponseDto getUserInfo(Long id) {
        return userRepository.findById(id).map(UserResponseDto::new).orElseThrow(() -> new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다."));
    }

    /**
     * 유저 정보를 수정합니다. 본인 확인 및 이메일 중복 검증을 수행합니다.
     * @param userId 현재 로그인한 유저 ID
     * @param id 수정 대상 유저 ID
     * @param dto 수정할 데이터
     * @return 수정된 유저 정보
     * @throws CustomException.ResourceNotFoundException 유저가 존재하지 않을 경우 발생
     * @throws CustomException.UnauthorizedException 수정 권한이 없을 경우 발생
     * @throws IllegalArgumentException 중복된 이메일로 수정을 시도할 경우 발생
     */
    @Override
    @Transactional
    public UserResponseDto updateUserInfo(Long userId, Long id, UserRequestDto dto) {

        User user = userRepository.findById(id).orElseThrow(() -> new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다."));

        if(!user.getUserId().equals(userId)) {
            throw new CustomException.UnauthorizedException("작성하신 본인만 접근 가능합니다.");
        }

        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        return new UserResponseDto(user.update(dto.getUserName(), dto.getEmail()));
    }

    /**
     * 유저를 삭제(탈퇴)합니다. 본인 확인 절차를 포함합니다.
     * @param userId 요청 유저 ID
     * @param id 삭제 대상 유저 ID
     * @throws CustomException.ResourceNotFoundException 유저가 존재하지 않을 경우 발생
     * @throws CustomException.UnauthorizedException 삭제 권한이 없을 경우 발생
     */
    @Override
    @Transactional
    public void deleteUser(Long userId, Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다."));

        if(!user.getUserId().equals(userId)) {
            throw new CustomException.UnauthorizedException("작성하신 본인만 접근 가능합니다.");
        }

        userRepository.deleteById(id);
    }

    /**
     * 유저의 비밀번호를 인증합니다.
     * @param dto 로그인 시도 정보 (이메일, 비밀번호)
     * @return 인증 성공 여부
     */
    @Override
    public boolean authenticate(UserRequestDto dto) {
        return userRepository.findByEmail(dto.getEmail()).map(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword())).orElse(false);
    }

    /**
     * 이메일을 통해 유저 정보를 조회합니다.
     * @param email 조회할 이메일 주소
     * @return 유저 정보
     * @throws CustomException.ResourceNotFoundException 이메일에 해당하는 유저가 없을 경우 발생
     */
    @Override
    public UserResponseDto getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponseDto::new).orElseThrow(() -> new CustomException.ResourceNotFoundException("입력한 정보와 일치한 유저는 존재하지 않습니다."));
    }

    /**
     * 특정 일정에 댓글을 작성합니다.
     * @param scheduleId 대상 일정 ID
     * @param loginUserId 작성자 유저 ID
     * @param dto 댓글 내용
     * @return 저장된 댓글 정보
     * @throws CustomException.ResourceNotFoundException 일정이나 유저가 존재하지 않을 경우 발생
     */
    @Override
    @Transactional
    public CommentResponseDto saveComment(Long scheduleId, Long loginUserId, CommentRequestDto dto) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다."));

        User user = userRepository.findById(loginUserId).orElseThrow(() -> new CustomException.ResourceNotFoundException("존재하지 않는 유저입니다."));

        return new CommentResponseDto(commentRepository.save(Comment.builder().content(dto.getContent()).schedule(schedule).user(user).build()));
    }

    /**
     * 특정 일정의 댓글 목록을 조회합니다.
     * @param scheduleId 일정 ID
     * @return 해당 일정의 댓글 리스트
     */
    @Override
    public List<CommentResponseDto> getCommentList(Long scheduleId) {
        return commentRepository.findAllBySchedule_ScheduleId(scheduleId).stream().map(CommentResponseDto::new).toList();
    }

    /**
     * 댓글 단건 정보를 조회합니다. 일정 소속 여부를 검증합니다.
     * @param scheduleId 일정 ID
     * @param commentId 댓글 ID
     * @return 댓글 상세 정보
     * @throws CustomException.ResourceNotFoundException 댓글이 없거나 해당 일정에 속하지 않을 경우 발생
     */
    @Override
    public CommentResponseDto getComment(Long scheduleId, Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if(!comment.getSchedule().getScheduleId().equals(scheduleId)) {
            throw new CustomException.ResourceNotFoundException("찾으시는 정보가 존재하지 않습니다.");
        }

        return new CommentResponseDto(comment);
    }

    /**
     * 댓글 내용을 수정합니다. 작성자 본인 확인 절차를 포함합니다.
     * @param scheduleId 일정 ID
     * @param commentId 댓글 ID
     * @param userId 요청 유저 ID
     * @param dto 수정할 내용
     * @return 수정된 댓글 정보
     * @throws CustomException.UnauthorizedException 작성자가 아닐 경우 발생
     */
    @Override
    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, Long userId, CommentRequestDto dto) {

        Comment comment = commentRepository.findById(commentId).orElseThrow();


        if(!comment.getUser().getUserId().equals(userId)) {
            throw new CustomException.UnauthorizedException("작성하신 본인만 접근 가능합니다.");
        }

        return new CommentResponseDto(comment.update(dto.getContent()));
    }

    /**
     * 댓글을 삭제합니다. 작성자 본인 확인 절차를 포함합니다.
     * @param scheduleId 일정 ID
     * @param commentId 댓글 ID
     * @param userId 요청 유저 ID
     * @throws CustomException.UnauthorizedException 작성자가 아닐 경우 발생
     */
    @Override
    @Transactional
    public void deleteComment(Long scheduleId, Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if(!comment.getUser().getUserId().equals(userId)) {
            throw new CustomException.UnauthorizedException("작성하신 본인만 접근 가능합니다.");
        }

        commentRepository.deleteById(commentId);
    }


}
