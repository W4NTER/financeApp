package ru.vadim.finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.finance.dto.request.OperationRequestDTO;
import ru.vadim.finance.dto.response.OperationResponseDTO;
import ru.vadim.finance.service.OperationService;

import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {
    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping
    public ResponseEntity<OperationResponseDTO> addOperation(@RequestBody OperationRequestDTO operation) {
        return new ResponseEntity<>(operationService.add(operation), HttpStatus.OK);
    }

    @DeleteMapping("/{operation_id}")
    public ResponseEntity<Void> deleteOperation(
            @PathVariable(name = "operation_id") Long operationId) {
        operationService.delete(operationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{type}/{category_id}")
    public ResponseEntity<List<OperationResponseDTO>> findAllByType(
            @PathVariable String type,
            @PathVariable(name = "category_id") Long categoryId) {
        return new ResponseEntity<>(
                operationService.findAllByTypeAndCategoryId(type, categoryId), HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<List<OperationResponseDTO>> findAll(@PathVariable("category_id") Long categoryId) {
        return new ResponseEntity<>(operationService.findAllByCategoryId(categoryId), HttpStatus.OK);
    }
}
