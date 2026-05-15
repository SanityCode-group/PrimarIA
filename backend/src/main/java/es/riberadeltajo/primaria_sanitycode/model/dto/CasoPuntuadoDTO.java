package es.riberadeltajo.primaria_sanitycode.model.dto;

/**
 * Resumen de un caso clínico con su puntuación media.
 */
public class CasoPuntuadoDTO {

    private Long   idCaso;
    private String agente;
    private String categoria;
    private String diagnostico;
    private double puntuacion;

    public CasoPuntuadoDTO(Object[] row) {
        this.idCaso     = ((Number) row[0]).longValue();
        this.agente     = (String) row[1];
        this.categoria  = (String) row[2];
        this.diagnostico = (String) row[3];
        this.puntuacion = row[4] != null
                ? Math.round(((Number) row[4]).doubleValue() * 100.0) / 100.0
                : 0.0;
    }

    public Long   getIdCaso()      { return idCaso; }
    public String getAgente()      { return agente; }
    public String getCategoria()   { return categoria; }
    public String getDiagnostico() { return diagnostico; }
    public double getPuntuacion()  { return puntuacion; }
}