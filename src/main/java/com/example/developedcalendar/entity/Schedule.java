package com.example.developedcalendar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column()
    private String title;

    @Column()
    private String content;

    @Column()
    private String writerName;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime writeDate;

    @LastModifiedDate
    private LocalDateTime editDate;

}
