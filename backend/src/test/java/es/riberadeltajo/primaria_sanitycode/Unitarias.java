package es.riberadeltajo.primaria_sanitycode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoMuestraRepository;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoMuestraService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Unitarias {
    @Mock
    private CasoClinicoMuestraRepository repo;

    @InjectMocks
    private CasoClinicoMuestraService service;

    private CasoClinicoOriginal original;
    private CasoClinicoMuestra muestra;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

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
    public void testObtenerCasoClinicoAleatorio() {
        when(repo.findRandomCasoClinico()).thenReturn(muestra);

        CasoClinicoMuestra resultado = service.obtenerCasoClinicoAleatorio();

        assertNotNull(resultado);
        assertEquals("40", resultado.getCasoOriginal().getEdad());
        assertEquals("Migraña", resultado.getCasoOriginal().getDiagnostico_final());

        verify(repo, times(1)).findRandomCasoClinico();
    }

    @Test
    public void testObtenerCasoClinicoAleatorioNoExiste() {
        when(repo.findRandomCasoClinico()).thenReturn(null);

        CasoClinicoMuestra resultado = service.obtenerCasoClinicoAleatorio();

        assertNull(resultado);
        verify(repo, times(1)).findRandomCasoClinico();
    }
}