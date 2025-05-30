package ru.vadim.tgbot.client;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vadim.tgbot.dto.CategoryDTO;

@Component
@AllArgsConstructor
public class CategoryWebClient {
    private final WebClient webClient;
    private static final String CATEGORY_URI = "/category";
    private static final String HEADER_CHAT_ID = "Tg-Chat-Id";
    private static final String URI_FORMAT = "%s/%s";

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
                .uri(String.format(URI_FORMAT, CATEGORY_URI, title))
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String setCategoryLimit(Long chatId, CategoryDTO categoryDTO) {
        return webClient
                .put()
                .uri(CATEGORY_URI)
                .header(HEADER_CHAT_ID, String.valueOf(chatId))
                .body(Mono.just(categoryDTO), CategoryDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
