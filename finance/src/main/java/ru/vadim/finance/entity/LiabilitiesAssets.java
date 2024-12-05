package ru.vadim.finance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "liabilities_assets")
@Getter
@Setter
@RequiredArgsConstructor
public class LiabilitiesAssets {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public LiabilitiesAssets(Chat chat,
                             String title,
                             String type,
                             Integer sum,
                             OffsetDateTime createdAt) {
        this.chat = chat;
        this.title = title;
        this.type = type;
        this.sum = sum;
        this.createdAt = createdAt;
    }
}
