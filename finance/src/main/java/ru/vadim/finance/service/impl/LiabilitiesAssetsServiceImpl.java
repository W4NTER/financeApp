package ru.vadim.finance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.finance.dto.request.LiabilitiesAssetsRequest;
import ru.vadim.finance.dto.response.LiabilitiesAssetsResponse;
import ru.vadim.finance.entity.Chat;
import ru.vadim.finance.entity.LiabilitiesAssets;
import ru.vadim.finance.exception.EntityAlreadyExistsException;
import ru.vadim.finance.exception.EntityNotFoundException;
import ru.vadim.finance.repository.ChatRepository;
import ru.vadim.finance.repository.LiabilitiesAssetsRepository;
import ru.vadim.finance.service.ChatService;
import ru.vadim.finance.service.LiabilitiesAssetsService;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LiabilitiesAssetsServiceImpl implements LiabilitiesAssetsService {
    private final LiabilitiesAssetsRepository liabilitiesAssetsRepository;
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;
    private final static String LIABILITY_TYPE = "LIABILITY";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public LiabilitiesAssetsResponse addLiabilitiesAssets(LiabilitiesAssetsRequest request) {
        var liabilitiesAssets = liabilitiesAssetsRepository.findByTitleAndChatId(request.title(), request.chatId());
        if (liabilitiesAssets.isPresent()) {
            throw new EntityAlreadyExistsException(LiabilitiesAssets.class.getSimpleName());
        }
        return objectMapper.convertValue(liabilitiesAssetsRepository.save(
                new LiabilitiesAssets(
                        chatRepository.findByChatId(request.chatId()).orElseThrow(() ->
                                new EntityNotFoundException(Chat.class.getSimpleName())),
                        request.title(),
                        request.type(),
                        request.sum(),
                        OffsetDateTime.now())),
                LiabilitiesAssetsResponse.class);
    }

    @Override
    public List<LiabilitiesAssetsResponse> findAllByChatId(Long chatId) {
        return liabilitiesAssetsRepository.findAllByChatId(chatId).stream()
                .map(ob -> objectMapper.convertValue(ob, LiabilitiesAssetsResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteAllByChatId(Long chatId) {
        liabilitiesAssetsRepository.deleteAllByChatId(chatId);
    }

    @Override
    public Integer calculateResult(Long chatId) {
        var listObj =  findAllByChatId(chatId);
        Integer sum = listObj.stream()
                .mapToInt(ob -> ob.type().equals(LIABILITY_TYPE) ? -ob.sum() : ob.sum())
                .sum();
        LOGGER.info(String.format("ChatId = %d, liability/assets sum = %d", chatId, sum));
        return sum;
    }
}
