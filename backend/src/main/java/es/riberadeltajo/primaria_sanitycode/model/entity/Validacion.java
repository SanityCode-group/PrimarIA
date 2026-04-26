package es.riberadeltajo.primaria_sanitycode.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/*
CREATE TABLE IF NOT EXISTS validaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- Campos de valoración (1 a 5)
    precision_diagnostica TINYINT NOT NULL CHECK (precision_diagnostica BETWEEN 1 AND 5),
    claridad_textual TINYINT NOT NULL CHECK (claridad_textual BETWEEN 1 AND 5),
    relevancia_clinica TINYINT NOT NULL CHECK (relevancia_clinica BETWEEN 1 AND 5),
    adecuacion_contextual TINYINT NOT NULL CHECK (adecuacion_contextual BETWEEN 1 AND 5),
    nivel_tecnico TINYINT NOT NULL CHECK (nivel_tecnico BETWEEN 1 AND 5),
    -- Campo de texto sin límite de caracteres
    observaciones LONGTEXT,
    -- Relaciones
    id_usuario BIGINT NOT NULL,
    id_caso_original INT NOT NULL,
    -- Fecha del registro
    fecha_validacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- Foreign keys
    CONSTRAINT fk_validacion_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id),
    CONSTRAINT fk_validacion_caso_original
        FOREIGN KEY (id_caso_original)
        REFERENCES casos_clinicos_originales(id),
	UNIQUE KEY (id_usuario, id_caso_original)
);
*/
@Entity
@Table(name = "validaciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_usuario", "id_caso_original" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Validacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precision_diagnostica", nullable = false)
    private Integer precisionDiagnostica;

    @Column(name = "claridad_textual", nullable = false)
    private Integer claridadTextual;

    @Column(name = "relevancia_clinica", nullable = false)
    private Integer relevanciaClinica;

    @Column(name = "adecuacion_contextual", nullable = false)
    private Integer adecuacionContextual;

    @Column(name = "nivel_tecnico", nullable = false)
    private Integer nivelTecnico;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caso_original", nullable = false)
    private CasoClinicoOriginal casoOriginal;

    @Column(name = "fecha_validacion", nullable = false)
    @Builder.Default
    private LocalDateTime fechaValidacion = LocalDateTime.now();
}
