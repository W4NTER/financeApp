package ru.vadim.tgbot.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vadim.tgbot.dto.CategoryDTO;
import ru.vadim.tgbot.dto.request.BalanceRequest;
import ru.vadim.tgbot.dto.request.OperationDTO;

@Component
public class FinanceAppWebClient {
    private final WebClient webClient;
    private static final String TG_CHAT_URI = "/tg-chat/";
    private static final String CATEGORY_URI = "/category";
    private static final String OPERATION_URI = "/operation";
    private static final String BALANCE_URI = "/balance";
    private static final String INCREMENT_URI = "/increment";
    private static final String DECREMENT_URI = "/decrement";
    private static final String RESET_URI = "/reset";
    private static final String HEADER_CHAT_ID = "Tg-Chat-Id";

    public FinanceAppWebClient(WebClient webClient) {
        this.webClient = webClient;
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

    public String addCategory(Long chatId, String category) {
        var categoryDTO = new CategoryDTO(category, null);
        return webClient
                .post()
                .uri(CATEGORY_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(categoryDTO), CategoryDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteCategory(Long chatId) {
        return webClient
                .delete()
                .uri(CATEGORY_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String allCategoriesOfChat(Long chatId) {
        return webClient
                .get()
                .uri(CATEGORY_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String findCategoryByTitleAndChatId(Long chatId, String title) {
        return webClient
                .get()
                .uri(String.format("%s/%s", CATEGORY_URI, title))
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String setLimit(Long chatId, CategoryDTO categoryDTO) {
        return webClient
                .put()
                .uri(CATEGORY_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .body(Mono.just(categoryDTO), CategoryDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String addOperation(OperationDTO operationDTO) {
        return webClient
                .post()
                .uri(OPERATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(operationDTO), OperationDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteOperation(Long operationId) {
        return webClient
                .delete()
                .uri(String.format("%s/%s", OPERATION_URI, operationId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String findAllOperationsByCategoryAndType(String type, Long categoryId) {
        return webClient
                .get()
                .uri(String.format("%s/%s/%s", OPERATION_URI, type, categoryId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String findAllOperationsByCategory(Long categoryId) {
        return webClient
                .get()
                .uri(String.format("%s/%s", OPERATION_URI, categoryId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

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
                .uri(String.format("%s/%s",BALANCE_URI, INCREMENT_URI))
                .body(Mono.just(request), BalanceRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String decrementBalance(BalanceRequest request) {
        return webClient
                .put()
                .uri(String.format("%s/%s",BALANCE_URI, DECREMENT_URI))
                .body(Mono.just(request), BalanceRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String resetBalance(Long chatId) {
        return webClient
                .put()
                .uri(String.format("%s/%s",BALANCE_URI, RESET_URI))
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
