package com.example.developedcalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevelopedCalendarApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevelopedCalendarApplication.class, args);
    }

}
