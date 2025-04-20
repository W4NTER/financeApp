package ru.vadim.finance.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.starter_t1.aspect.annotation.LogException;
import ru.starter_t1.aspect.annotation.LogExecution;
import ru.starter_t1.aspect.annotation.LogTracking;
import ru.vadim.finance.service.ChartService;

@RestController
@RequestMapping("/chart")
@AllArgsConstructor
public class ChartController {
    private final ChartService chartService;

    @LogExecution
    @LogException
    @LogTracking
    @SneakyThrows
    @PostMapping
    public ResponseEntity<Void> getChart(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        chartService.generateChart(chatId);
        return ResponseEntity.ok().build();
    }
}
