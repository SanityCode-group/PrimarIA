package es.riberadeltajo.primaria_sanitycode.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidacionRequest {
    private Long idCasoOriginal;
    private Integer precisionDiagnostica;
    private Integer claridadTextual;
    private Integer relevanciaClinica;
    private Integer adecuacionContextual;
    private Integer nivelTecnico;
    private String observaciones;
}
