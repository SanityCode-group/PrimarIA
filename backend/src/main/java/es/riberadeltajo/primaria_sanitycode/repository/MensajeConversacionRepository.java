package es.riberadeltajo.primaria_sanitycode.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.riberadeltajo.primaria_sanitycode.model.entity.MensajeConversacion;

public interface MensajeConversacionRepository extends JpaRepository<MensajeConversacion, Long> {
    List<MensajeConversacion> findByConversacionIdOrderByOrdenAsc(Long idConversacion);
}