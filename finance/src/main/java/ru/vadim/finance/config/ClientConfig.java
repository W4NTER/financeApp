package ru.vadim.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    private final ApplicationConfiguration config;

    public ClientConfig(ApplicationConfiguration applicationConfiguration) {
        this.config = applicationConfiguration;
    }

    @Bean
    public WebClient financeClient() {
        return WebClient.builder().baseUrl(config.baseUrlBot()).build();
    }
}
