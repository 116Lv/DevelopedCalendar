package com.example.developedcalendar.repository;

import com.example.developedcalendar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
