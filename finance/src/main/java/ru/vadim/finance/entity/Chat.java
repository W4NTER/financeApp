package ru.vadim.finance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "region")
    private String region;

    @JsonBackReference
    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<Category> categories;

    @JsonBackReference
    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<LiabilitiesAssets> liabilitiesAssets;

    public Chat() {
    }

    public Chat(
            Long chatId,
            OffsetDateTime createdAt) {
        this.chatId = chatId;
        this.createdAt = createdAt;
        this.enable = true;
    }

    public Chat(
            Long chatId,
            OffsetDateTime createdAt,
            Boolean enable) {
        this.chatId = chatId;
        this.createdAt = createdAt;
        this.enable = enable;
    }

    public Long getChatId() {
        return chatId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<LiabilitiesAssets> getLiabilitiesAssets() {
        return liabilitiesAssets;
    }

    public void setLiabilitiesAssets(List<LiabilitiesAssets> liabilitiesAssets) {
        this.liabilitiesAssets = liabilitiesAssets;
    }
}
