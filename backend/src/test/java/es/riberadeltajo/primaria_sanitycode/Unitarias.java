package es.riberadeltajo.primaria_sanitycode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoMuestraRepository;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoMuestraService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Unitarias {
    @Mock
    private CasoClinicoMuestraRepository repo;

    @InjectMocks
    private CasoClinicoMuestraService service;

    private CasoClinicoOriginal original;
    private CasoClinicoMuestra muestra;

    @BeforeEach
    public void setUp() {
        System.out.println("\n=== INICIO TEST UNITARIO ===");

        original = CasoClinicoOriginal.builder()
                .id(1)
                .edad("40")
                .sexo("M")
                .motivo("Dolor de cabeza")
                .diagnostico_final("Migraña")
                .build();

        muestra = CasoClinicoMuestra.builder()
                .id(1L)
                .casoOriginal(original)
                .build();
    }

    @Test
    public void testObtenerCasoClinicoAleatorio_OK() {
        System.out.println("Test: obtener caso clínico correcto");

        when(repo.findRandomCasoClinico()).thenReturn(muestra);

        CasoClinicoMuestra resultado = service.obtenerCasoClinicoAleatorio();

        System.out.println("Resultado: " + resultado.getId());
        System.out.println("Edad: " + resultado.getCasoOriginal().getEdad());
        System.out.println("Diagnóstico: " + resultado.getCasoOriginal().getDiagnostico_final());

        assertNotNull(resultado);
        assertNotNull(resultado.getCasoOriginal());

        System.out.println("✔ TEST OK");
    }

    @Test
    public void testObtenerCasoClinicoAleatorio_NoExiste() {
        System.out.println("Test: caso clínico no existe");

        when(repo.findRandomCasoClinico()).thenReturn(null);

        CasoClinicoMuestra resultado = service.obtenerCasoClinicoAleatorio();

        System.out.println("Resultado: " + resultado);

        assertNull(resultado);

        System.out.println("✔ TEST OK (resultado null esperado)");
    }
}