USE primaria_db;

DROP TABLE IF EXISTS casos_clinicos_muestra;
DROP TABLE IF EXISTS whitelist;


RENAME TABLE casos_clinicos_originales TO casos_clinicos;

ALTER TABLE validaciones
    DROP FOREIGN KEY fk_validacion_caso_original;

ALTER TABLE validaciones
    ADD CONSTRAINT fk_validacion_caso
        FOREIGN KEY (id_caso_original)
        REFERENCES casos_clinicos(id);


ALTER TABLE validaciones
    MODIFY precision_diagnostica TINYINT NULL,
    MODIFY claridad_textual TINYINT NULL,
    MODIFY relevancia_clinica TINYINT NULL,
    MODIFY adecuacion_contextual TINYINT NULL,
    MODIFY nivel_tecnico TINYINT NULL;


-- NUEVAS COLUMNAS DE ESTADO
ALTER TABLE validaciones
    ADD COLUMN estado ENUM('pendiente', 'completada') NOT NULL DEFAULT 'pendiente' AFTER observaciones,
    ADD COLUMN fecha_asignacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER estado;


-- PROCEDIMIENTO DE ASIGNACIÓN
DROP PROCEDURE IF EXISTS asignar_casos;

DELIMITER $$
CREATE PROCEDURE asignar_casos()
BEGIN
    INSERT IGNORE INTO validaciones 
        (id_usuario, id_caso_original, estado, fecha_asignacion,
         precision_diagnostica, claridad_textual, relevancia_clinica, 
         adecuacion_contextual, nivel_tecnico)
    SELECT 
        u.id,
        c.id,
        'pendiente',
        NOW(),
        NULL, NULL, NULL, NULL, NULL
    FROM casos_clinicos c
    JOIN usuarios u ON u.rol = 'medico'
    WHERE 
        (SELECT COUNT(*) FROM validaciones v WHERE v.id_caso_original = c.id) < 2
        AND NOT EXISTS (
            SELECT 1 FROM validaciones v 
            WHERE v.id_usuario = u.id AND v.id_caso_original = c.id
        )
    ORDER BY 
        (SELECT COUNT(*) FROM validaciones v2 WHERE v2.id_usuario = u.id AND v2.estado = 'pendiente') ASC;
END$$
DELIMITER ;