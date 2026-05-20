package es.riberadeltajo.primaria_sanitycode.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.riberadeltajo.primaria_sanitycode.model.entity.Conversacion;

public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {
    List<Conversacion> findByIdUsuario(Long idUsuario);
}