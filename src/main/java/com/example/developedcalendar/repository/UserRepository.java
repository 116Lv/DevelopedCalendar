package com.example.developedcalendar.repository;

import com.example.developedcalendar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * User 엔티티에 대한 데이터 액세스 기능을 제공하는 리포지토리 인터페이스입니다.
 * 회원 정보 관리 및 이메일을 이용한 인증 로직에 필요한 쿼리 메서드를 정의합니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 모든 유저 목록을 유저 ID 기준 내림차순(최신 가입순)으로 조회합니다.
     * @return 정렬된 User 엔티티 리스트
     */
    List<User> findAllByOrderByUserIdDesc();

    /**
     * 이메일 주소를 통해 유저 정보를 조회합니다.
     * @param email 조회하고자 하는 유저의 이메일
     * @return 유저 정보를 담고 있는 Optional 객체 (해당 이메일이 없을 경우 빈 상태)
     */
    Optional<User> findByEmail(String email);
}
