package es.riberadeltajo.primaria_sanitycode.service;

import es.riberadeltajo.primaria_sanitycode.model.dto.ValidacionRequest;
import es.riberadeltajo.primaria_sanitycode.model.entity.*;
import es.riberadeltajo.primaria_sanitycode.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class ValidacionServiceTest {
    @InjectMocks
    private ValidacionService service;

    @Mock
    private ValidacionRepository validacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CasoClinicoOriginalRepository casoRepository;

    private ValidacionRequest request;
    private Usuario usuario;
    private CasoClinicoOriginal caso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        System.out.println("\n=== TEST VALIDACION SERVICE ===");

        request = new ValidacionRequest(1L,5,5,5,5,5,"Todo correcto");

        usuario = new Usuario();
        usuario.setEmail("test@test.com");

        caso = new CasoClinicoOriginal();
        caso.setId(1L);
    }

    @Test
    void testGuardarValidacion_OK() {
        System.out.println("Test: guardar validación OK");

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(casoRepository.findById(1L)).thenReturn(Optional.of(caso));

        service.guardarValidacion(request, "test@test.com");

        verify(validacionRepository, times(1)).save(any(Validacion.class));

        System.out.println("✔ Validación guardada correctamente");
    }

    @Test
    void testUsuarioNoExiste() {
        System.out.println("Test: usuario no existe");

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        Exception e = assertThrows(RuntimeException.class, () -> {
            service.guardarValidacion(request, "no@test.com");
        });

        System.out.println("Error esperado: " + e.getMessage());

        System.out.println("✔ Test usuario no existe OK");
    }

    @Test
    void testCasoNoExiste() {
        System.out.println("Test: caso no existe");

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        when(casoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.guardarValidacion(request, "test@test.com");
        });

        System.out.println("✔ Test caso no existe OK");
    }
}