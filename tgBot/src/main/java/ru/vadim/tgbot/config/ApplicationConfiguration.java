package ru.vadim.tgbot.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfiguration(
        @NotEmpty
        String telegramToken,

        @NotNull
        String baseUrlFinanceApp
) {
}
