package es.riberadeltajo.primaria_sanitycode.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SecurityTest {

    @Test
    void contextoCarga() {
        System.out.println("✔ Seguridad cargada correctamente");
    }
}