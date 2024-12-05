package ru.vadim.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column(name = "category_id", unique = true, nullable = false)
    private Long categoryId;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "category_limit")
    private Integer categoryLimit;

}
