package ru.vadim.tgbot.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FinanceAppWebClient {
    private final WebClient webClient;
    private static final String TG_CHAT_URI = "/tg-chat/";
    private static final String CHART_URI = "/chart";
    private static final String EXCEL_REPORT_URI = "/excel";
    private static final String HEADER_CHAT_ID = "Tg-Chat-Id";

    public FinanceAppWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String chartGenerate(Long chatId) {
        return webClient
                .post()
                .uri(CHART_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String excelReport(Long chatId) {
        return webClient
                .post()
                .uri(EXCEL_REPORT_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String registerChat(Long chatId) {
        return webClient
                .post()
                .uri(TG_CHAT_URI + chatId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteChat(Long chatId) {
        return webClient
                .delete()
                .uri(TG_CHAT_URI + chatId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    //TODO заглушка, сервер я еще не менял
    public String setRegion(Long chatId, String region) {
        return "Region added successfully";
    }
}
