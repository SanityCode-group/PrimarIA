package es.riberadeltajo.primaria_sanitycode.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/*
CREATE TABLE casos_clinicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- Relación con el caso original
    id_original INT NOT NULL,
    -- Estado del caso clínico
    estado VARCHAR(30) NOT NULL,
    fecha DATETIME NOT NULL,
    -- Copia editable de los campos del caso original
    edad VARCHAR(50),
    sexo VARCHAR(50),
    antecedentes_medicos TEXT,
    antecedentes_quirurgicos TEXT,
    habitos TEXT,
    situacion_basal TEXT,
    medicacion_actual TEXT,
    antecedentes_familiares TEXT,
    motivo TEXT,
    sintomas TEXT,
    exploracion_general TEXT,
    signos TEXT,
    resultados_pruebas TEXT,
    razonamiento_clinico TEXT,
    diagnostico_final TEXT,
    tratamiento_farmacologico TEXT,
    tratamiento_no_farmacologico TEXT,
    factores_sociales TEXT,
    alergias TEXT,
    referencias_bibliograficas TEXT,
    categoria VARCHAR(255),
    keywords TEXT,
    codigo_cie_10 TEXT,
    dificultad VARCHAR(50),
    chunk_id VARCHAR(255),
    chunk VARCHAR(255),
    id_medico BIGINT NOT NULL,

    -- Foreign key SIEMPRE al final
    CONSTRAINT fk_caso_original
		FOREIGN KEY (id_original)
        REFERENCES casos_clinicos_originales(id),

    CONSTRAINT fk_caso_medico
		FOREIGN KEY (id_medico)
		REFERENCES usuarios(id)
);
 */

@Entity
@Table(name = "casos_clinicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CasoClinico {

    //id INT AUTO_INCREMENT PRIMARY KEY,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //id_original INT NOT NULL, con foreign key a casos_clinicos_originales(id)
    @Column(name = "id_original", nullable = false)
    private long id_original;

    //estado VARCHAR(30) NOT NULL,
    @Column(nullable = false, length = 30)
    private String estado;

    //fecha DATETIME NOT NULL,
    @Column(nullable = false)
    private LocalDateTime fecha;

    //edad VARCHAR(50),
    @Column(name = "edad")
    private String edad;

    //sexo VARCHAR(50),
    @Column(name = "sexo")
    private String sexo;

    //antecedentes_medicos TEXT,
    @Column(name = "antecedentes_medicos", columnDefinition = "TEXT")
    private String antecedentes_medicos;

    //antecedentes_quirurgicos TEXT,
    @Column(name = "antecedentes_quirurgicos", columnDefinition = "TEXT")
    private String antecedentes_quirurgicos;

    //habitos TEXT,
    @Column(name = "habitos", columnDefinition = "TEXT")
    private String habitos;

    //situacion_basal TEXT,
    @Column(name = "situacion_basal", columnDefinition = "TEXT")
    private String situacion_basal;

    //medicacion_actual TEXT,
    @Column(name = "medicacion_actual", columnDefinition = "TEXT")
    private String medicacion_actual;

    //antecedentes_familiares TEXT,
    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentes_familiares;

    //motivo TEXT,
    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    //sintomas TEXT,
    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas;

    //exploracion_general TEXT,
    @Column(name = "exploracion_general", columnDefinition = "TEXT")
    private String exploracion_general;

    //signos TEXT,
    @Column(name = "signos", columnDefinition = "TEXT")
    private String signos;

    //resultados_pruebas TEXT,
    @Column(name = "resultados_pruebas", columnDefinition = "TEXT")
    private String resultados_pruebas;

    //razonamiento_clinico TEXT,
    @Column(name = "razonamiento_clinico", columnDefinition = "TEXT")
    private String razonamiento_clinico;

    //diagnostico_final TEXT,
    @Column(name = "diagnostico_final", columnDefinition = "TEXT")
    private String diagnostico_final;

    //tratamiento_farmacologico TEXT,
    @Column(name = "tratamiento_farmacologico", columnDefinition = "TEXT")
    private String tratamiento_farmacologico;

    //tratamiento_no_farmacologico TEXT,
    @Column(name = "tratamiento_no_farmacologico", columnDefinition = "TEXT")
    private String tratamiento_no_farmacologico;

    //factores_sociales TEXT,
    @Column(name = "factores_sociales", columnDefinition = "TEXT")
    private String factores_sociales;

    //alergias TEXT,
    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    //referencias_bibliograficas TEXT,
    @Column(name = "referencias_bibliograficas", columnDefinition = "TEXT")
    private String referencias_bibliograficas;

    //categoria VARCHAR(255),
    @Column(name = "categoria")
    private String categoria;

    //keywords TEXT,
    @Column(name = "keywords", columnDefinition = "TEXT")
    private String keywords;

    //codigo_cie_10 TEXT,
    @Column(name = "codigo_cie_10", columnDefinition = "TEXT")
    private String codigo_cie_10;

    //dificultad VARCHAR(50),
    @Column(name = "dificultad")
    private String dificultad;

    //chunk_id VARCHAR(255),
    @Column(name = "chunk_id")
    private String chunk_id;

    //chunk VARCHAR(255)
    @Column(name = "chunk")
    private String chunk;

    //id_medico BIGINT NOT NULL,
    @Column(name = "id_medico", nullable = false)
    private Long id_medico;

    // Constructor vacío requerido por JPA
    //public CasoClinico() {}
}


