package es.riberadeltajo.primaria_sanitycode.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.riberadeltajo.primaria_sanitycode.model.dto.ApiRequest;
import es.riberadeltajo.primaria_sanitycode.model.dto.ApiResponse;
import es.riberadeltajo.primaria_sanitycode.model.dto.ChatMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AiChatService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Configurable desde application.properties
    public AiChatService(
            @Value("${ai.api.base-url:http://cloud.riberadeltajo.es:11200}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String sendMessage(List<ChatMessage> history, String userMessage) {
        ChatMessage newMessage = new ChatMessage("user", userMessage);
        ApiRequest request = new ApiRequest(history, newMessage);

        try {
            // La API devuelve un array: [{"generated_text": "..."}]
            String rawJson = webClient.post()
                    .uri("/generate/")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ApiResponse[] responses = objectMapper.readValue(rawJson, ApiResponse[].class);

            if (responses != null && responses.length > 0 && responses[0].getGeneratedText() != null) {
                return responses[0].getGeneratedText();
            }
            return "No se obtuvo respuesta del modelo.";

        // línea 50-52 — cambia:
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar con la API de IA: " + e.getMessage(), e);
        }
        
    }
}