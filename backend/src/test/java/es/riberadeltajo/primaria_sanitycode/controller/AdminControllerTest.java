package es.riberadeltajo.primaria_sanitycode.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void accesoDenegadoUsuarioNormal() throws Exception {
        mockMvc.perform(get("/api/admin/usuarios")
                .with(user("user").roles("USER")))
                .andExpect(status().isForbidden());

        System.out.println("✔ Usuario normal bloqueado correctamente");
    }

    @Test
    void accesoPermitidoAdmin() throws Exception {
        mockMvc.perform(get("/api/admin/usuarios")
                .with(oauth2Login().attributes(attrs -> {
                    attrs.put("email", "admin@test.com");
                })))
                .andExpect(status().isOk());

        System.out.println("✔ Admin accede correctamente");
    }
}