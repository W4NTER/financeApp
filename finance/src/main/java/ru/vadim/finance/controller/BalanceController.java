package ru.vadim.finance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.finance.dto.request.BudgetRequest;
import ru.vadim.finance.dto.response.BudgetResponse;
import ru.vadim.finance.service.BudgetService;

@RestController
@RequestMapping("/balance")
@AllArgsConstructor
public class BalanceController {
    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<BudgetResponse> findBalanceByChatId(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(budgetService.findByChatId(chatId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> addBalance(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(budgetService.addBalance(chatId), HttpStatus.OK);
    }

    @PutMapping("/increment")
    public ResponseEntity<BudgetResponse> incrementBalance(
            @RequestBody BudgetRequest budgetRequest) {
        return new ResponseEntity<>(budgetService.incrementBalance(budgetRequest), HttpStatus.OK);
    }

    @PutMapping("/decrement")
    public ResponseEntity<BudgetResponse> decrementBalance(
            @RequestBody BudgetRequest budgetRequest) {
        return new ResponseEntity<>(budgetService.decrementBalance(budgetRequest), HttpStatus.OK);
    }

    @PutMapping("/reset")
    public ResponseEntity<BudgetResponse> resetBalance(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(budgetService.resetBalance(chatId), HttpStatus.OK);
    }
}
