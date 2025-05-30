package ru.vadim.finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.finance.dto.response.ChatResponseDTO;
import ru.vadim.finance.service.ChatService;

@RestController
@RequestMapping("/tg-chat/{id}")
public class TgChatController {
    private final ChatService chatService;

    public TgChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponseDTO> registerChat(@PathVariable Long id) {
        return new ResponseEntity<>(chatService.add(id), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.delete(id);
        return ResponseEntity.ok().build();
    }
}
