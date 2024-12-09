package ru.vadim.finance.client;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vadim.finance.dto.request.LimitNotificationRequest;

@Component
@AllArgsConstructor
public class BotClient {
    private final WebClient client;
    private static final String LIMIT_URL = "/limit";

    public void sendLimitNotification(LimitNotificationRequest request) {
        client
            .post()
            .uri(LIMIT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), LimitNotificationRequest.class)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
