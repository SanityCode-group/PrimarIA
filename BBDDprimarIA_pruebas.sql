-- Crear tablas

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    ultimo_acceso DATETIME
);


CREATE TABLE whitelist (
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
    chunk VARCHAR(255)
);


CREATE TABLE casos_clinicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- Relación con el caso original
    id_original INT NOT NULL,
    -- Estado del caso clínico
    estado VARCHAR(30) NOT NULL,
    fecha DATETIME NOT NULL,
    -- Copia de los campos del caso original
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
