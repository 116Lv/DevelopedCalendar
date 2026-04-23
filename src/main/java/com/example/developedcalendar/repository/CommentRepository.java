package com.example.developedcalendar.repository;

import com.example.developedcalendar.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Comment 엔티티에 대한 데이터 액세스 기능을 제공하는 리포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 및 쿼리 메서드 기능을 수행합니다.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 일정(Schedule)의 고유 식별자를 조건으로 해당 일정에 달린 모든 댓글 목록을 조회합니다.
     * * @param scheduleId 조회하고자 하는 일정의 고유 ID
     * @return 해당 일정에 포함된 Comment 엔티티 리스트
     */
    List<Comment> findAllBySchedule_ScheduleId(Long scheduleId);
}
