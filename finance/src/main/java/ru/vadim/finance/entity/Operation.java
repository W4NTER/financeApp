package ru.vadim.finance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

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
            String description) {
        this.type = type;
        this.sum = sum;
        this.createdAt = createdAt;
        this.category = category;
        this.description = description;
    }

    public Operation() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
