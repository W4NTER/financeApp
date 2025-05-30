package ru.vadim.finance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "chat")
@Getter
@Setter
@RequiredArgsConstructor
public class Chat {
    @Id
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "enable")
    private Boolean enable;

    @JsonBackReference
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Category> categories;

    @JsonBackReference
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<LiabilitiesAssets> liabilitiesAssets;

    public Chat(
            Long chatId,
            OffsetDateTime createdAt) {
        this.chatId = chatId;
        this.createdAt = createdAt;
        this.enable = true;
    }
}
