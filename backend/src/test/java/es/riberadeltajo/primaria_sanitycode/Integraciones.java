package es.riberadeltajo.primaria_sanitycode;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoMuestraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Integraciones {

    @Autowired
    private CasoClinicoMuestraService service;

    @Test
    void testObtenerCasoClinicoAleatorioIntegracion() {
        // Obtener un caso clínico aleatorio desde la BD
        CasoClinicoMuestra resultado = service.obtenerCasoClinicoAleatorio();

        assertNotNull(resultado, "No se obtuvo ningún caso clínico de la BD");
        assertNotNull(resultado.getCasoOriginal(), "El caso clínico no tiene asociado un original");
        assertNotNull(resultado.getCasoOriginal().getEdad(), "El caso original debe tener edad");
        assertNotNull(resultado.getCasoOriginal().getDiagnostico_final(), "El caso original debe tener diagnóstico final");
    }
}