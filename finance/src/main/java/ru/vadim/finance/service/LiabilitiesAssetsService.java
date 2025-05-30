package ru.vadim.finance.service;

import ru.vadim.finance.dto.request.LiabilitiesAssetsRequest;
import ru.vadim.finance.dto.response.LiabilitiesAssetsResponse;

import java.util.List;

public interface LiabilitiesAssetsService {
    LiabilitiesAssetsResponse addLiabilitiesAssets(LiabilitiesAssetsRequest request);

    List<LiabilitiesAssetsResponse> findAllByChatId(Long chatId);

    void deleteAllByChatId(Long chatId);

    Integer calculateResult(Long chatId);
}
