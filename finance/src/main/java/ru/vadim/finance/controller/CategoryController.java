package ru.vadim.finance.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.finance.dto.request.CategoryRequestDTO;
import ru.vadim.finance.dto.response.CategoryResponseDTO;
import ru.vadim.finance.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addCategory(
            @RequestHeader("Tg-Chat-Id") Long chatId,
            @RequestBody CategoryRequestDTO category) {
        LOGGER.info(category);
        return new ResponseEntity<>(categoryService.add(category, chatId), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(
            @RequestHeader("Tg-Chat-Id") Long chatId,
            @RequestBody CategoryRequestDTO category) {
        categoryService.delete(category, chatId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategoriesByChat(
            @RequestHeader("Tg-Chat-Id") Long chatId) {
        return new ResponseEntity<>(categoryService.findAllByChatId(chatId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDTO> setLimit(
            @RequestHeader("Tg-Chat-Id") Long chatId,
            @RequestBody CategoryRequestDTO category) {
        return new ResponseEntity<>(categoryService.setLimit(category, chatId), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<CategoryResponseDTO> findCategoryByTitleAndChat(
            @RequestHeader("Tg-Chat-Id") Long chatId,
            @PathVariable("title") String title) {
        return new ResponseEntity<>(categoryService.findCategoryByTitleAndChatId(title, chatId), HttpStatus.OK);
    }
}
