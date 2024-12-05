package ru.vadim.tgbot.client;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vadim.tgbot.dto.request.OperationDTO;

@Component
@AllArgsConstructor
public class OperationsWebClient {
    private final WebClient webClient;
    private static final String OPERATION_URI = "/operation";
    private static final String URI_FORMAT = "%s/%s";

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
                .uri(String.format(URI_FORMAT, OPERATION_URI, operationId))
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
                .uri(String.format(URI_FORMAT, OPERATION_URI, categoryId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
