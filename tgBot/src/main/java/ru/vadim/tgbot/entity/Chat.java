package ru.vadim.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Chat {

    @Id
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column(name = "state")
    private String state;

    public Chat(Long chatId, String state) {
        this.chatId = chatId;
        this.state = state;
    }
}
