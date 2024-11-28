package ru.vadim.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "state")
    private String state;
}
