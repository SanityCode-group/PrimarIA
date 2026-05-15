package es.riberadeltajo.primaria_sanitycode.model.dto;

/**
 * Puntuaciones medias de un modelo de IA agrupadas por agente.
 */
public class MetricaAgenteDTO {

    private String agente;
    private double avgPrecision;
    private double avgClaridad;
    private double avgRelevancia;
    private double avgAdecuacion;
    private double avgNivel;
    private double avgTotal;
    private long totalValidaciones;

    public MetricaAgenteDTO(Object[] row) {
        this.agente            = (String)  row[0];
        this.avgPrecision      = toDouble(row[1]);
        this.avgClaridad       = toDouble(row[2]);
        this.avgRelevancia     = toDouble(row[3]);
        this.avgAdecuacion     = toDouble(row[4]);
        this.avgNivel          = toDouble(row[5]);
        this.totalValidaciones = toLong(row[6]);
        this.avgTotal = round((avgPrecision + avgClaridad + avgRelevancia
                               + avgAdecuacion + avgNivel) / 5.0);
    }

    private double toDouble(Object o) {
        if (o == null) return 0.0;
        return round(((Number) o).doubleValue());
    }
    private long toLong(Object o) {
        if (o == null) return 0L;
        return ((Number) o).longValue();
    }
    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public String getAgente()             { return agente; }
    public double getAvgPrecision()       { return avgPrecision; }
    public double getAvgClaridad()        { return avgClaridad; }
    public double getAvgRelevancia()      { return avgRelevancia; }
    public double getAvgAdecuacion()      { return avgAdecuacion; }
    public double getAvgNivel()           { return avgNivel; }
    public double getAvgTotal()           { return avgTotal; }
    public long   getTotalValidaciones()  { return totalValidaciones; }
}