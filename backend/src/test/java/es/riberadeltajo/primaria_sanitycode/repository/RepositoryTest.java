package es.riberadeltajo.primaria_sanitycode.repository;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {
    @Autowired
    private CasoClinicoMuestraRepository repo;

    @Test
    public void testQuery() {
        System.out.println("\n=== TEST REPOSITORIO ===");

        CasoClinicoMuestra resultado = repo.findCasoValido(1L);

        System.out.println("Caso obtenido: " + resultado.getId());

        assertNotNull(resultado);

        System.out.println("✔ Query OK");
    }
}