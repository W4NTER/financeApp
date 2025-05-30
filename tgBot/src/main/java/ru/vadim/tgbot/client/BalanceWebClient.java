package ru.vadim.tgbot.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vadim.tgbot.dto.request.BalanceRequest;

@Component
@AllArgsConstructor
public class BalanceWebClient {
    private final WebClient webClient;
    private static final String BALANCE_URI = "/balance";
    private static final String INCREMENT_URI = "/increment";
    private static final String DECREMENT_URI = "/decrement";
    private static final String RESET_URI = "/reset";
    private static final String HEADER_CHAT_ID = "Tg-Chat-Id";
    private static final String URI_FORMAT = "%s/%s";

    public String findBalanceById(Long chatId) {
        return webClient
                .get()
                .uri(BALANCE_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String addBalance(Long chatId) {
        return webClient
                .post()
                .uri(BALANCE_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String incrementBalance(BalanceRequest request) {
        return webClient
                .put()
                .uri(String.format(URI_FORMAT, BALANCE_URI, INCREMENT_URI))
                .body(Mono.just(request), BalanceRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String decrementBalance(BalanceRequest request) {
        return webClient
                .put()
                .uri(String.format(URI_FORMAT, BALANCE_URI, DECREMENT_URI))
                .body(Mono.just(request), BalanceRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String resetBalance(Long chatId) {
        return webClient
                .put()
                .uri(String.format(URI_FORMAT, BALANCE_URI, RESET_URI))
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
