package es.riberadeltajo.primaria_sanitycode.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import es.riberadeltajo.primaria_sanitycode.model.dto.ChatMessage;
import es.riberadeltajo.primaria_sanitycode.model.entity.MensajeConversacion;
import es.riberadeltajo.primaria_sanitycode.repository.ConversacionRepository;
import es.riberadeltajo.primaria_sanitycode.repository.MensajeConversacionRepository;

@Service
public class ConversacionService {

    private final ConversacionRepository conversacionRepo;
    private final MensajeConversacionRepository mensajeRepo;

    public ConversacionService(ConversacionRepository c, MensajeConversacionRepository m) {
        this.conversacionRepo = c;
        this.mensajeRepo = m;
    }

    public List<ChatMessage> obtenerHistorial(Long idConversacion) {
        return mensajeRepo.findByConversacionIdOrderByOrdenAsc(idConversacion)
                .stream()
                .map(m -> new ChatMessage(
                        m.getRole().name(),
                        m.getContent()
                ))
                .toList();
    }

    public void guardarMensaje(Long idConversacion, String role, String content) {
        List<MensajeConversacion> mensajes =
                mensajeRepo.findByConversacionIdOrderByOrdenAsc(idConversacion);

        int orden = mensajes.size();

        MensajeConversacion m = new MensajeConversacion();
        
        var conversacion = conversacionRepo.findById(idConversacion);
        if (conversacion.isEmpty()) {
            throw new RuntimeException("Conversación no existe: " + idConversacion);
        }

        m.setConversacion(conversacion.get());
        m.setRole(MensajeConversacion.Role.valueOf(role));
        m.setContent(content);
        m.setOrden(orden);
        m.setFechaCreacion(LocalDateTime.now());

        mensajeRepo.save(m);
    }
}
