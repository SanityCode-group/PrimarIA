package es.riberadeltajo.primaria_sanitycode.repository;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author María C García Correas
 * @since 11/1/26
 */
@Repository
public interface CasoClinicoRepository extends JpaRepository<CasoClinico, Long> {
}
