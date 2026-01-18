package es.riberadeltajo.primaria_sanitycode.repository;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author María C García Correas
 * @since 6/1/26
 */
@Repository
public interface CasoClinicoOriginalRepository extends JpaRepository<CasoClinicoOriginal, Long> {

    @Query(value = "SELECT * FROM casos_clinicos_originales ORDER BY RAND() LIMIT 1", nativeQuery = true)
    CasoClinicoOriginal findRandomCasoClinico();
}
