package ru.vadim.finance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.finance.dto.request.LiabilitiesAssetsRequest;
import ru.vadim.finance.dto.response.LiabilitiesAssetsResponse;
import ru.vadim.finance.service.LiabilitiesAssetsService;

import java.util.List;

@RestController
@RequestMapping("/liabilitiesAssets")
@AllArgsConstructor
public class LiabilitiesAssetsController {
    private final LiabilitiesAssetsService liabilitiesAssetsService;

    @GetMapping
    public ResponseEntity<List<LiabilitiesAssetsResponse>> findAllByChatId(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(liabilitiesAssetsService.findAllByChatId(chatId), HttpStatus.OK);
    }

    @GetMapping("/result")
    public ResponseEntity<Integer> calculate(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(liabilitiesAssetsService.calculateResult(chatId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LiabilitiesAssetsResponse> addLiabilityOrAsset(
            @RequestBody LiabilitiesAssetsRequest request) {
        return new ResponseEntity<>(liabilitiesAssetsService.addLiabilitiesAssets(request), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllByChat(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        liabilitiesAssetsService.deleteAllByChatId(chatId);
        return ResponseEntity.ok().build();
    }

}
