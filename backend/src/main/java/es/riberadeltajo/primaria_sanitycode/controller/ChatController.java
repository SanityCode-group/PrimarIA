package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.model.dto.ChatMessage;
import es.riberadeltajo.primaria_sanitycode.model.dto.ChatRequest;
import es.riberadeltajo.primaria_sanitycode.model.dto.ChatResponse;
import es.riberadeltajo.primaria_sanitycode.service.AiChatService;
import es.riberadeltajo.primaria_sanitycode.service.ConversacionService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Ajusta al origen de tu frontend en producción
public class ChatController {

    private final AiChatService aiChatService;
    private final ConversacionService conversacionService;

    public ChatController(AiChatService aiChatService, ConversacionService conversacionService) {
        this.aiChatService = aiChatService;
        this.conversacionService = conversacionService;
    }

    @PostMapping("/{idConversacion}/message")
    public ResponseEntity<ChatResponse> sendMessage(
            @PathVariable Long idConversacion,
            @RequestBody ChatRequest request
    ) {
        try {
            // 1. Obtener historial REAL desde BD
            List<ChatMessage> history = conversacionService.obtenerHistorial(idConversacion);

            // 2. Guardar mensaje del usuario
            conversacionService.guardarMensaje(idConversacion, "user", request.getMessage());

            // 3. Llamar a IA
            String reply = aiChatService.sendMessage(history, request.getMessage());

            // 4. Guardar respuesta IA
            conversacionService.guardarMensaje(idConversacion, "assistant", reply);

            return ResponseEntity.ok(ChatResponse.ok(reply));

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(ChatResponse.error(e.getMessage()));
        }
    }
}