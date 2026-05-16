package es.riberadeltajo.primaria_sanitycode.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiChatServiceTest {

    @Autowired
    private AiChatService service;

    @Test
    void sendMessage_noFalla() {
        System.out.println("\n=== TEST AI CHAT SERVICE ===");
        
        String result = service.sendMessage(
                java.util.List.of(),
                "Hola"
        );

        org.junit.jupiter.api.Assertions.assertNotNull(result);

        System.out.println("✔ Respuesta obtenida: " + result);
    }
}