package es.riberadeltajo.primaria_sanitycode.repository;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author María C García Correas
 * @since 11/1/26
 */
@Repository
public interface CasoClinicoMuestraRepository extends JpaRepository<CasoClinicoMuestra, Long> {

    @Query(value = "SELECT * FROM casos_clinicos_muestra ORDER BY RAND() LIMIT 1", nativeQuery = true)
    CasoClinicoMuestra findRandomCasoClinico();

}


