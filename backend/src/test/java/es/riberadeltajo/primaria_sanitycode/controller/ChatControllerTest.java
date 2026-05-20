package es.riberadeltajo.primaria_sanitycode.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import es.riberadeltajo.primaria_sanitycode.model.entity.Conversacion;
import es.riberadeltajo.primaria_sanitycode.repository.ConversacionRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConversacionRepository conversacionRepo;

    @Test
    void chatFunciona() throws Exception {
        System.out.println("\n=== TEST: Chat funciona ===");

        Conversacion c = new Conversacion();
        c.setFechaCreacion(java.time.LocalDateTime.now());
        c.setIdUsuario(1L);
        c = conversacionRepo.save(c);

        String json = """
            {
                "message": "Hola"
            }
        """;

        mockMvc.perform(post("/api/chat/" + c.getId() + "/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        System.out.println("✔ Chat OK");
    }
}