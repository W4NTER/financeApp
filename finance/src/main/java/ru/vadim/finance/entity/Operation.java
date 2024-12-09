package ru.vadim.finance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private Long operationId;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public Operation(
            String type,
            Integer sum,
            OffsetDateTime createdAt,
            Category category,
            String title) {
        this.type = type;
        this.sum = sum;
        this.createdAt = createdAt;
        this.category = category;
        this.title = title;
    }
}
