package ru.vadim.finance.client;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vadim.finance.dto.request.ChartRequest;
import ru.vadim.finance.dto.request.ExcelReportRequest;
import ru.vadim.finance.dto.request.LimitNotificationRequest;

@Component
@AllArgsConstructor
public class BotClient {
    private final WebClient client;
    private static final String LIMIT_URI = "/limit";
    private static final String CHART_URI = "/chart";
    private static final String EXCEL_URI = "/excel";

    public void sendLimitNotification(LimitNotificationRequest request) {
        client
            .post()
            .uri(LIMIT_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), LimitNotificationRequest.class)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    public void sendChart(ChartRequest request) {
        client
                .post()
                .uri(CHART_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), ChartRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void excelReport(ExcelReportRequest request) {
        client
                .post()
                .uri(EXCEL_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), ChartRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
