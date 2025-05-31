package ru.vadim.finance.entity;

import jakarta.persistence.*;
import ru.vadim.finance.utils.BudgetStatusEnum;

import java.time.LocalDate;

@Entity
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum")
    private Integer sum = 0;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "chatId", nullable = false)
    private Chat chat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BudgetStatusEnum status;

    public Budget() {
    }

    public Budget(
            Integer sum,
            LocalDate startDate,
            LocalDate endDate,
            Chat chat,
            BudgetStatusEnum status
            ) {
        this.sum = sum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.chat = chat;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
