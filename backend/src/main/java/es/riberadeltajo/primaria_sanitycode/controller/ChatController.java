package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.model.dto.ChatRequest;
import es.riberadeltajo.primaria_sanitycode.model.dto.ChatResponse;
import es.riberadeltajo.primaria_sanitycode.service.AiChatService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Ajusta al origen de tu frontend en producción
public class ChatController {

    private final AiChatService aiChatService;

    public ChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        try {
            String reply = aiChatService.sendMessage(
                    request.getHistory(),
                    request.getMessage()
            );
            return ResponseEntity.ok(ChatResponse.ok(reply));
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(ChatResponse.error("Error al procesar la solicitud: " + e.getMessage()));
        }
    }
}