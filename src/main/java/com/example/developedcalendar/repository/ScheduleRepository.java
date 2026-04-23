package com.example.developedcalendar.repository;

import com.example.developedcalendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Schedule 엔티티에 대한 데이터 액세스 기능을 제공하는 리포지토리 인터페이스입니다.
 * 일정 정보의 저장, 조회, 수정, 삭제를 담당합니다.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 전체 일정 목록을 일정 ID(PK) 기준으로 오름차순 정렬하여 조회합니다.
     * @return 정렬된 Schedule 엔티티 리스트
     */
    List<Schedule> findAllByOrderByScheduleIdAsc();
}
