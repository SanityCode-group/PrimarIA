package es.riberadeltajo.primaria_sanitycode.repository;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author María C García Correas
 * @since 11/1/26
 */
@Repository
public interface CasoClinicoMuestraRepository extends JpaRepository<CasoClinicoMuestra, Long> {

    /*
     * Devuelve un caso clínico de muestra que el usuario no haya validado 
     * y que no haya sido validado por al menos 2 usuarios.
    */
    @Query(value = """
        SELECT m.*
        FROM casos_clinicos_muestra m
        WHERE m.id_original NOT IN (
            SELECT v.id_caso_original
            FROM validaciones v
            WHERE v.id_usuario = :usuarioId
        )
        AND (
            SELECT COUNT(DISTINCT v2.id_usuario)
            FROM validaciones v2
            WHERE v2.id_caso_original = m.id_original
        ) < 2
        ORDER BY RAND()
        LIMIT 1
    """, nativeQuery = true)
    CasoClinicoMuestra findCasoValido(@Param("usuarioId") Long usuarioId);
}



//     @Query(value = """
//     SELECT m.*
//     FROM casos_clinicos_muestra m
//     LEFT JOIN validaciones v_user
//         ON v_user.id_caso_original = m.id_original
//         AND v_user.id_usuario = :usuarioId
//     WHERE v_user.id IS NULL
//     AND (
//         SELECT COUNT(DISTINCT v2.id_usuario)
//         FROM validaciones v2
//         WHERE v2.id_caso_original = m.id_original
//     ) < 2
//     AND m.id_original >= FLOOR(RAND() * (
//         SELECT MAX(id_original)
//         FROM casos_clinicos_muestra
//     ))
//     LIMIT 1
//     """, nativeQuery = true)
//     CasoClinicoMuestra findCasoClinicoDisponible(@Param("usuarioId") Long usuarioId);

// }


