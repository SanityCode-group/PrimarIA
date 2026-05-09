package es.riberadeltajo.primaria_sanitycode.repository;

import es.riberadeltajo.primaria_sanitycode.model.entity.Validacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionRepository extends JpaRepository<Validacion, Long> {

    boolean existsByUsuarioIdAndCasoOriginalId(Long usuarioId, Long casoId);

    @Query("""
        SELECT COUNT(DISTINCT v.usuario.id)
        FROM Validacion v
        WHERE v.casoOriginal.id = :casoId
    """)
    long countDistinctUsuariosByCasoOriginalId(Long casoId);
}
