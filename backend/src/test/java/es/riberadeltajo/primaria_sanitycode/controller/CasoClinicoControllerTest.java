package es.riberadeltajo.primaria_sanitycode.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CasoClinicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sinLoginDebeFallar() throws Exception {
        System.out.println("\n=== TEST: Sin login ===");

        mockMvc.perform(get("/api/casos/random"))
                .andExpect(status().isUnauthorized());

        System.out.println("✔ Bloqueo sin login OK");
    }

    @Test
    void conLoginDebeFuncionar() throws Exception {
        System.out.println("\n=== TEST: Con login ===");

        mockMvc.perform(get("/api/casos/random")
        .with(oauth2Login()
                .attributes(attrs -> attrs.put("email", "test@test.com"))))
        .andExpect(status().isOk());

        System.out.println("✔ Acceso con login OK");
    }
}