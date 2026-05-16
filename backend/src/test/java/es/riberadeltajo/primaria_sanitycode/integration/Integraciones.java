package es.riberadeltajo.primaria_sanitycode.integration;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoMuestraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Integraciones {
    @Autowired
    private CasoClinicoMuestraService service;

    @Test
    public void testObtenerCasoClinicoAleatorioIntegracion() {
        System.out.println("\n=== TEST INTEGRACIÓN: Obtener caso clínico ===");

        CasoClinicoMuestra resultado = service.obtenerCasoClinicoAleatorio(1L);

        System.out.println("Caso obtenido: " + resultado.getId());

        if (resultado != null && resultado.getCasoOriginal() != null) {
            System.out.println("Edad: " + resultado.getCasoOriginal().getEdad());
            System.out.println("Diagnóstico: " + resultado.getCasoOriginal().getDiagnostico_final());
        }

        assertNotNull(resultado);
        assertNotNull(resultado.getCasoOriginal());
        assertNotNull(resultado.getCasoOriginal().getEdad());
        assertNotNull(resultado.getCasoOriginal().getDiagnostico_final());

        System.out.println("✔ TEST INTEGRACIÓN OK");
    }

    @Test
    public void testMultiplesLlamadas() {
        System.out.println("\n=== TEST INTEGRACIÓN: múltiples llamadas ===");

        for (int i = 0; i < 3; i++) {
            CasoClinicoMuestra resultado = service.obtenerCasoClinicoAleatorio(1L);

            System.out.println("Iteración " + i + ": " + resultado.getId());

            assertNotNull(resultado);
        }

        System.out.println("✔ TEST MÚLTIPLES OK");
    }
}