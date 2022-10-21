package com.project.devcat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Chat {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue
    private Long id;
    private String message;
    private LocalDateTime send_time;

}
