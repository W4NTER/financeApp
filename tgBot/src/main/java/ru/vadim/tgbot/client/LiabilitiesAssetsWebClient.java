package ru.vadim.tgbot.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vadim.tgbot.dto.request.LiabilitiesAssetsRequest;

@Component
public class LiabilitiesAssetsWebClient {
    private final WebClient client;
    private static final String LIABILITIES_ASSETS_URI = "liabilitiesAssets";
    private static final String HEADER_CHAT_ID = "Tg-Chat-Id";
    private static final String RESULT_URI = "result";

    public LiabilitiesAssetsWebClient(WebClient client) {
        this.client = client;
    }

    public String addLiabilityOrAsset(LiabilitiesAssetsRequest request) {
        return client
                .post()
                .uri(LIABILITIES_ASSETS_URI)
                .body(Mono.just(request), LiabilitiesAssetsRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String findAllByChatId(Long chatId) {
        return client
                .get()
                .uri(LIABILITIES_ASSETS_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteAllByChatId(Long chatId) {
        return client
                .delete()
                .uri(LIABILITIES_ASSETS_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getResult(Long chatId) {
        return client
                .get()
                .uri(String.format("%s/%s", LIABILITIES_ASSETS_URI, RESULT_URI))
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
