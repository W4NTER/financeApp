package ru.vadim.finance.controller;

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
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addCategory(@RequestBody CategoryRequestDTO category) {
        return new ResponseEntity<>(categoryService.add(category), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@RequestBody CategoryRequestDTO category) {
        categoryService.delete(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{chat_id}")
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategoriesByChat(
            @PathVariable(name = "chat_id") Long chatId) {
        return new ResponseEntity<>(categoryService.findAllByChatId(chatId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDTO> setLimit(@RequestBody CategoryRequestDTO category) {
        return new ResponseEntity<>(categoryService.setLimit(category), HttpStatus.OK);
    }
}
