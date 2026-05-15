package es.riberadeltajo.primaria_sanitycode.repository;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.model.entity.Usuario;
import es.riberadeltajo.primaria_sanitycode.model.entity.Validacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionRepository extends JpaRepository<Validacion, Long> {

    boolean existsByUsuarioIdAndCasoOriginalId(Long usuarioId, Long casoId);

    boolean existsByUsuarioAndCasoOriginal(Usuario usuario, CasoClinicoOriginal casoOriginal);

    @Query("""
        SELECT COUNT(DISTINCT v.usuario.id)
        FROM Validacion v
        WHERE v.casoOriginal.id = :casoId
    """)
    long countDistinctUsuariosByCasoOriginalId(Long casoId);


    

    //  MÉTRICAS ADMIN 

    /**
     * Puntuación media de cada criterio agrupada por modelo de IA (campo agente
     * de CasoClinicoOriginal). Devuelve filas: [agente, avgPrecision, avgClaridad,
     * avgRelevancia, avgAdecuacion, avgNivel, totalValidaciones]
     */
    @Query("""
        SELECT
            c.agente,
            AVG(v.precisionDiagnostica),
            AVG(v.claridadTextual),
            AVG(v.relevanciaClinica),
            AVG(v.adecuacionContextual),
            AVG(v.nivelTecnico),
            COUNT(v.id)
        FROM Validacion v
        JOIN v.casoOriginal c
        WHERE c.agente IS NOT NULL
        GROUP BY c.agente
        ORDER BY AVG(v.precisionDiagnostica +
                     v.claridadTextual +
                     v.relevanciaClinica +
                     v.adecuacionContextual +
                     v.nivelTecnico) DESC
    """)
    List<Object[]> metricasPorAgente();

    /**
     * Media global de cada criterio sobre todas las validaciones.
     * Devuelve una sola fila: [avgPrecision, avgClaridad, avgRelevancia,
     *                          avgAdecuacion, avgNivel]
     */
    @Query("""
        SELECT
            AVG(v.precisionDiagnostica),
            AVG(v.claridadTextual),
            AVG(v.relevanciaClinica),
            AVG(v.adecuacionContextual),
            AVG(v.nivelTecnico)
        FROM Validacion v
    """)
    Object[] mediaGlobalCriterios();

    /**
     * Casos con puntuación máxima (5) en precisión diagnóstica.
     */
    @Query("""
        SELECT v.casoOriginal.id, v.casoOriginal.agente,
               v.casoOriginal.categoria, v.casoOriginal.diagnostico_final,
               AVG(v.precisionDiagnostica)
        FROM Validacion v
        GROUP BY v.casoOriginal.id, v.casoOriginal.agente,
                 v.casoOriginal.categoria, v.casoOriginal.diagnostico_final
        HAVING AVG(v.precisionDiagnostica) = 5
        ORDER BY v.casoOriginal.id
    """)
    List<Object[]> casosPerfectosPrecision();

    /**
     * Casos con puntuación máxima (5) en claridad textual.
     */
    @Query("""
        SELECT v.casoOriginal.id, v.casoOriginal.agente,
               v.casoOriginal.categoria, v.casoOriginal.diagnostico_final,
               AVG(v.claridadTextual)
        FROM Validacion v
        GROUP BY v.casoOriginal.id, v.casoOriginal.agente,
                 v.casoOriginal.categoria, v.casoOriginal.diagnostico_final
        HAVING AVG(v.claridadTextual) = 5
        ORDER BY v.casoOriginal.id
    """)
    List<Object[]> casosPerfectosClaridad();

    /**
     * Top 10 casos por puntuación media total (suma de los 5 criterios).
     */
    @Query("""
        SELECT
            v.casoOriginal.id,
            v.casoOriginal.agente,
            v.casoOriginal.categoria,
            v.casoOriginal.diagnostico_final,
            AVG(v.precisionDiagnostica + v.claridadTextual +
                v.relevanciaClinica + v.adecuacionContextual + v.nivelTecnico)
        FROM Validacion v
        GROUP BY v.casoOriginal.id, v.casoOriginal.agente,
                 v.casoOriginal.categoria, v.casoOriginal.diagnostico_final
        ORDER BY AVG(v.precisionDiagnostica + v.claridadTextual +
                     v.relevanciaClinica + v.adecuacionContextual + v.nivelTecnico) DESC
        LIMIT 10
    """)
    List<Object[]> top10CasosPorPuntuacion();

    /** Total de validaciones registradas */
    @Query("SELECT COUNT(v) FROM Validacion v")
    long totalValidaciones();

    /** Total de casos ya validados (con al menos una validación) */
    @Query("SELECT COUNT(DISTINCT v.casoOriginal.id) FROM Validacion v")
    long totalCasosValidados();
}
