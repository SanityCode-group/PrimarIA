package es.riberadeltajo.primaria_sanitycode.service;

import es.riberadeltajo.primaria_sanitycode.model.entity.Conversacion;
import es.riberadeltajo.primaria_sanitycode.model.entity.MensajeConversacion;
import es.riberadeltajo.primaria_sanitycode.repository.ConversacionRepository;
import es.riberadeltajo.primaria_sanitycode.repository.MensajeConversacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConversacionServiceTest {

    @InjectMocks
    private ConversacionService service;

    @Mock
    private ConversacionRepository conversacionRepo;

    @Mock
    private MensajeConversacionRepository mensajeRepo;

    @Test
    void guardarMensaje_creaTituloPrimerMensaje() {
        System.out.println("\n=== TEST: Guardar mensaje con primer mensaje de titulo ===");

        Conversacion c = new Conversacion();
        c.setId(1L);

        when(conversacionRepo.findById(1L)).thenReturn(Optional.of(c));
        when(mensajeRepo.findByConversacionIdOrderByOrdenAsc(1L))
                .thenReturn(List.of());

        service.guardarMensaje(1L, "user", "Hola soy el primer mensaje del chat");

        assertEquals("Hola soy el prime...", c.getTitulo());
        verify(conversacionRepo).save(c);
        verify(mensajeRepo).save(any(MensajeConversacion.class));

        System.out.println("✔ Mensaje guardado + título primer mensaje correctamente");
    }

    @Test
    void guardarMensaje_truncaTituloSiEsLargo() {
        System.out.println("\n=== TEST: Guardar mensaje con titulo largo ===");

        Conversacion c = new Conversacion();
        c.setId(1L);

        when(conversacionRepo.findById(1L)).thenReturn(Optional.of(c));
        when(mensajeRepo.findByConversacionIdOrderByOrdenAsc(1L))
                .thenReturn(List.of());

        String longText = "Este es un mensaje extremadamente largo para probar el título";

        service.guardarMensaje(1L, "user", longText);

        assertTrue(c.getTitulo().endsWith("..."));
        assertTrue(c.getTitulo().length() <= 20);

        System.out.println("✔ Mensaje guardado + titulo largo correctamente");
    }
}