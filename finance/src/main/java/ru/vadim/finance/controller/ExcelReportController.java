package ru.vadim.finance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vadim.finance.service.ExcelReportService;

@RestController
@RequestMapping("/excel")
public class ExcelReportController {
    private final ExcelReportService excelReportService;

    public ExcelReportController(ExcelReportService excelReportService) {
        this.excelReportService = excelReportService;
    }

    @PostMapping
    public ResponseEntity<Void> excelReport(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        excelReportService.generateReport(chatId);
        return ResponseEntity.ok().build();
    }
}
