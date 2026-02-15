-- Eliminar tablas en orden correcto (primero las dependientes) 
USE primaria_db;

DROP TABLE IF EXISTS casos_clinicos_muestra; 
DROP TABLE IF EXISTS casos_clinicos_originales; 
DROP TABLE IF EXISTS whitelist; 
DROP TABLE IF EXISTS usuarios; 
DROP TABLE IF EXISTS validaciones; 

-- Crear tablas

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    ultimo_acceso DATETIME
);

CREATE TABLE IF NOT EXISTS whitelist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    fecha_alta DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS casos_clinicos_originales (
    id INT AUTO_INCREMENT PRIMARY KEY,
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
    ia_generadora VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS casos_clinicos_muestra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- Relación con el caso original
    id_original INT NOT NULL,
        
    -- Foreign key SIEMPRE al final 
    CONSTRAINT fk_caso_original 
		FOREIGN KEY (id_original) 
        REFERENCES casos_clinicos_originales(id)
);

CREATE TABLE IF NOT EXISTS validaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- Campos de valoración (1 a 5)
    precision_diagnostica TINYINT NOT NULL CHECK (precision_diagnostica BETWEEN 1 AND 5),
    claridad_textual TINYINT NOT NULL CHECK (claridad_textual BETWEEN 1 AND 5),
    relevancia_clinica TINYINT NOT NULL CHECK (relevancia_clinica BETWEEN 1 AND 5),
    adecuacion_contextual TINYINT NOT NULL CHECK (adecuacion_contextual BETWEEN 1 AND 5),
    nivel_tecnico TINYINT NOT NULL CHECK (nivel_tecnico BETWEEN 1 AND 5),
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


-- PRUEBAS DE ACCESO

USE primaria_db;

SELECT *
FROM casos_clinicos_originales
LIMIT 2;


SELECT COUNT(*) FROM casos_clinicos_originales;

SELECT COUNT(*) FROM casos_clinicos_muestra;

