package ru.vadim.finance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "category_limit")
    private Integer limit;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Category(
            String title,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt,
            Chat chat,
            Integer limit) {
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.chat = chat;
        this.limit = limit;
    }
}
