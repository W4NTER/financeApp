package ru.vadim.finance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.finance.dto.request.CategoryRequestDTO;
import ru.vadim.finance.dto.response.CategoryResponseDTO;
import ru.vadim.finance.entity.Category;
import ru.vadim.finance.entity.Chat;
import ru.vadim.finance.entity.Operation;
import ru.vadim.finance.exception.EntityAlreadyExistsException;
import ru.vadim.finance.exception.EntityNotFoundException;
import ru.vadim.finance.repository.CategoryRepository;
import ru.vadim.finance.repository.ChatRepository;
import ru.vadim.finance.repository.OperationRepository;
import ru.vadim.finance.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;
    private final OperationRepository operationRepository;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            ObjectMapper objectMapper,
            ChatRepository chatRepository, OperationRepository operationRepository) {
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
        this.chatRepository = chatRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public CategoryResponseDTO add(CategoryRequestDTO category) {
        var categoryOptional =
                categoryRepository.findByTitleAndChatId(category.title(), category.chatId());
        if (categoryOptional.isPresent()) {
            throw new EntityAlreadyExistsException(Category.class.getSimpleName());
        } else {
            return objectMapper.convertValue(
                    categoryRepository.save(
                            new Category(
                                    category.title(),
                                    category.createdAt(),
                                    category.updatedAt(),
                                    chatRepository.findByChatId(category.chatId())
                                            .orElseThrow(() ->
                                                    new EntityNotFoundException(Chat.class.getSimpleName())),
                                    category.limit()

                            )
                    ), CategoryResponseDTO.class);
        }

    }

    @Override
    @Transactional
    public void delete(CategoryRequestDTO category) {
        Category categoryObj =
                categoryRepository.findByTitleAndChatId(category.title(), category.chatId())
                        .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName()));
        List<Operation> operations = operationRepository.findAllByCategory(categoryObj);

        operationRepository.deleteAll(operations);
        categoryRepository.delete(categoryObj);
    }

    @Override
    public List<CategoryResponseDTO> findAllByChatId(Long chatId) {
        return categoryRepository.findAllByChatId(chatId).stream()
                .map(category -> objectMapper.convertValue(category, CategoryResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public CategoryResponseDTO setLimit(CategoryRequestDTO category) {
        Category categoryObj = categoryRepository.findByTitleAndChatId(category.title(), category.chatId())
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName()));
        categoryObj.setLimit(category.limit());
        return objectMapper.convertValue(categoryRepository.save(categoryObj), CategoryResponseDTO.class);
    }
}
