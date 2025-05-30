package ru.vadim.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "balance")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Balance {
    @Id
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column(name = "sum")
    private Integer sum = 0;
}
