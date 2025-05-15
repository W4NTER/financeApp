package ru.vadim.tgbot.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
    @LoadBalanced
    public WebClient financeClient() {
        return WebClient.builder().baseUrl(config.baseUrlFinanceApp()).build();
    }
}
