package ru.vadim.finance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.starter_t1.aspect.annotation.LogExecution;
import ru.vadim.finance.dto.request.BalanceRequest;
import ru.vadim.finance.dto.response.BalanceResponse;
import ru.vadim.finance.service.BalanceService;

@RestController
@RequestMapping("/balance")
@AllArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @LogExecution
    @GetMapping
    public ResponseEntity<BalanceResponse> findBalanceByChatId(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(balanceService.findByChatId(chatId), HttpStatus.OK);
    }

    @PostMapping
    @LogExecution
    public ResponseEntity<BalanceResponse> addBalance(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(balanceService.addBalance(chatId), HttpStatus.OK);
    }

    @LogExecution
    @PutMapping("/increment")
    public ResponseEntity<BalanceResponse> incrementBalance(
            @RequestBody BalanceRequest balanceRequest) {
        return new ResponseEntity<>(balanceService.incrementBalance(balanceRequest), HttpStatus.OK);
    }

    @LogExecution
    @PutMapping("/decrement")
    public ResponseEntity<BalanceResponse> decrementBalance(
            @RequestBody BalanceRequest balanceRequest) {
        return new ResponseEntity<>(balanceService.decrementBalance(balanceRequest), HttpStatus.OK);
    }

    @LogExecution
    @PutMapping("/reset")
    public ResponseEntity<BalanceResponse> resetBalance(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(balanceService.resetBalance(chatId), HttpStatus.OK);
    }
}
