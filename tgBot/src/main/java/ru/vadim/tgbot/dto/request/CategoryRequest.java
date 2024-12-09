package ru.vadim.tgbot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryRequest(
        @JsonProperty("categoryId")
        Long categoryId,

        @JsonProperty("title")
        String title,

        @JsonProperty("limit")
        Integer categoryLimit
) {
}
