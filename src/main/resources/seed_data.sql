-- ================================================================
-- SEED DATA - Registro Colegio
-- ================================================================
-- Genera datos de prueba para todas las tablas del sistema:
--   ~506 Alumnas (1° a 6° Primaria + 1° a 5° Secundaria, secciones A y B)
--   ~60  Trabajadores
--   ~280 Padres/Apoderados
--   ~20  Personas Externas
--   ~11,700 Registros de Ingreso (6 meses)
--   ~1,800 Salidas de Alumnas
--   ~50  Encargos
--   5    Usuarios
--
-- EJECUTAR con MySQL:  mysql -u root -p registroC < seed_data.sql
-- O desde el cliente:  SOURCE seed_data.sql;
-- ================================================================

-- ---------------------------------------------------------------
-- LIMPIEZA DE DATOS EXISTENTES
-- ---------------------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE ingreso_personal;
TRUNCATE TABLE salidas;
TRUNCATE TABLE encargo;
TRUNCATE TABLE padre_alumna;
TRUNCATE TABLE padre_apoderado;
TRUNCATE TABLE alumna;
TRUNCATE TABLE trabajador;
TRUNCATE TABLE usuario;
SET SQL_SAFE_UPDATES = 0;
DELETE FROM persona WHERE id_persona >= 1;
SET SQL_SAFE_UPDATES = 1;
-- ROL no se trunca (ADMIN y USER deben existir)

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE persona AUTO_INCREMENT = 1;
ALTER TABLE ingreso_personal AUTO_INCREMENT = 1;
ALTER TABLE usuario AUTO_INCREMENT = 1;
ALTER TABLE salidas AUTO_INCREMENT = 1;
ALTER TABLE encargo AUTO_INCREMENT = 1;

-- ---------------------------------------------------------------
-- TABLAS TEMPORALES DE REFERENCIA
-- ---------------------------------------------------------------

-- Nombres para alumnas y personas en general
DROP TEMPORARY TABLE IF EXISTS temp_nombres_f;
CREATE TEMPORARY TABLE temp_nombres_f (nombre VARCHAR(50));
INSERT INTO temp_nombres_f (nombre) VALUES
('Maria'),('Lucia'),('Ana'),('Sofia'),('Camila'),('Valentina'),('Isabella'),
('Valeria'),('Mariana'),('Luciana'),('Jimena'),('Alejandra'),('Daniela'),
('Gabriela'),('Samantha'),('Fernanda'),('Adriana'),('Andrea'),('Fabiana'),
('Romina'),('Ariana'),('Natalia'),('Nicole'),('Paola'),('Renata'),('Abril'),
('Antonella'),('Briana'),('Carla'),('Diana'),('Emilia'),('Fatima'),('Gianella'),
('Hanna'),('Ivanna'),('Juliana'),('Kiara'),('Lola'),('Micaela'),('Nadia'),
('Olivia'),('Pamela'),('Rafaella'),('Sabrina'),('Tamara'),('Vanessa'),
('Wendy'),('Ximena'),('Yamile'),('Zoe'),('Rosa'),('Carmen'),('Teresa'),
('Elena'),('Patricia'),('Silvia'),('Claudia'),('Rocio'),('Milagros'),('Katherine');

-- Nombres masculinos para padres/trabajadores
DROP TEMPORARY TABLE IF EXISTS temp_nombres_m;
CREATE TEMPORARY TABLE temp_nombres_m (nombre VARCHAR(50));
INSERT INTO temp_nombres_m (nombre) VALUES
('Jose'),('Carlos'),('Luis'),('Juan'),('Miguel'),('Pedro'),('Jorge'),('Manuel'),
('Fernando'),('Ricardo'),('Alberto'),('Roberto'),('Cesar'),('Oscar'),('Marco'),
('Diego'),('Pablo'),('Rafael'),('Mario'),('Raul'),('Hector'),('Victor'),
('Sergio'),('Eduardo'),('Gustavo'),('Enrique'),('Francisco'),('Jaime'),
('Felipe'),('Hugo'),('Daniel'),('David'),('Julio'),('Ruben'),('Alex'),
('Christian'),('Renato'),('Giancarlo'),('Bruno'),('Joaquin'),('Walter'),
('Arturo'),('Ernesto'),('Alonso'),('Alfredo'),('Edwin'),('Henry'),('Ivan'),
('Angelo'),('Cristian'),('Franco'),('Leonardo'),('Martin'),('Nelson'),('Orlando'),
('Percy'),('Ramiro'),('Samuel'),('Teodoro'),('Wilson');

-- Apellidos
DROP TEMPORARY TABLE IF EXISTS temp_apellidos;
CREATE TEMPORARY TABLE temp_apellidos (apellido VARCHAR(50));
INSERT INTO temp_apellidos (apellido) VALUES
('Garcia'),('Rodriguez'),('Lopez'),('Martinez'),('Gonzalez'),('Perez'),
('Sanchez'),('Ramirez'),('Flores'),('Torres'),('Diaz'),('Morales'),('Cruz'),
('Ortiz'),('Gutierrez'),('Ruiz'),('Chavez'),('Mendoza'),('Vasquez'),
('Jimenez'),('Rios'),('Castillo'),('Herrera'),('Medina'),('Aguilar'),
('Castro'),('Paredes'),('Suarez'),('Huaman'),('Quispe'),('Mamani'),('Condori'),
('Vargas'),('Ramos'),('Reyes'),('Romero'),('Delgado'),('Campos'),('Vega'),
('Guzman'),('Soto'),('Sandoval'),('Leon'),('Mejia'),('Cabrera'),('Cardenas');

-- Grados y secciones (22 combinaciones)
DROP TEMPORARY TABLE IF EXISTS temp_grados;
CREATE TEMPORARY TABLE temp_grados (grado_num INT, nivel VARCHAR(50), seccion CHAR(1));
INSERT INTO temp_grados (grado_num, nivel, seccion) VALUES
(1,'Primaria','A'),(1,'Primaria','B'),
(2,'Primaria','A'),(2,'Primaria','B'),
(3,'Primaria','A'),(3,'Primaria','B'),
(4,'Primaria','A'),(4,'Primaria','B'),
(5,'Primaria','A'),(5,'Primaria','B'),
(6,'Primaria','A'),(6,'Primaria','B'),
(1,'Secundaria','A'),(1,'Secundaria','B'),
(2,'Secundaria','A'),(2,'Secundaria','B'),
(3,'Secundaria','A'),(3,'Secundaria','B'),
(4,'Secundaria','A'),(4,'Secundaria','B'),
(5,'Secundaria','A'),(5,'Secundaria','B');

-- Cargos de trabajadores
DROP TEMPORARY TABLE IF EXISTS temp_cargos;
CREATE TEMPORARY TABLE temp_cargos (cargo VARCHAR(100));
INSERT INTO temp_cargos (cargo) VALUES
('Docente de Matematica'),('Docente de Comunicacion'),('Docente de Ciencias'),
('Docente de Ingles'),('Docente de Computacion'),('Docente de Arte'),
('Docente de Educacion Fisica'),('Docente de Religion'),('Docente de Historia'),
('Docente de Geografia'),('Auxiliar de Primaria'),('Auxiliar de Secundaria'),
('Director'),('Subdirector'),('Coordinador Academico'),('Secretaria'),
('Psicologa'),('Enfermera'),('Portero'),('Personal de Limpieza'),
('Bibliotecaria'),('Tutora de Primaria'),('Tutora de Secundaria'),
('Administrativo'),('Contador'),('Jefe de Taller'),('Auxiliar de Laboratorio'),
('Coordinador de Deportes'),('Recepcionista'),('Jardinero');

-- Motivos de salida de alumnas
DROP TEMPORARY TABLE IF EXISTS temp_motivos_salida;
CREATE TEMPORARY TABLE temp_motivos_salida (motivo VARCHAR(255));
INSERT INTO temp_motivos_salida (motivo) VALUES
('Enfermedad'),('Cita medica'),('Permiso familiar'),('Retiro temprano'),
('Emergencia'),('Tramite personal'),('Actividad externa'),('Dental'),
('Malestar estomacal'),('Dolor de cabeza'),('Fiebre'),('Recoger documentos'),
('Entrevista'),('Competencia deportiva'),('Taller externo');

-- Motivos de ingreso para padres/externos
DROP TEMPORARY TABLE IF EXISTS temp_motivos_ingreso;
CREATE TEMPORARY TABLE temp_motivos_ingreso (motivo VARCHAR(255));
INSERT INTO temp_motivos_ingreso (motivo) VALUES
('Reunion con docente'),('Entrega de documentos'),('Recoger a alumna'),
('Reunion de apoderados'),('Tramite administrativo'),('Visita al colegio'),
('Entrevista con direccion'),('Traer almuerzo'),('Traer utiles olvidados'),
('Reunion con tutor'),('Coordinacion academica'),('Pago de pension'),
('Matricula'),('Entrega de libreta'),('Charla informativa');

-- Parentescos
DROP TEMPORARY TABLE IF EXISTS temp_parentescos;
CREATE TEMPORARY TABLE temp_parentescos (parentesco VARCHAR(50));
INSERT INTO temp_parentescos (parentesco) VALUES
('Madre'),('Padre'),('Abuela'),('Abuelo'),('Tia'),('Tio'),('Apoderada'),('Apoderado');

-- Encargos comunes
DROP TEMPORARY TABLE IF EXISTS temp_encargos;
CREATE TEMPORARY TABLE temp_encargos (encargo VARCHAR(255));
INSERT INTO temp_encargos (encargo) VALUES
('Paquete de libros'),('Material didactico'),('Documentos administrativos'),
('Utiles escolares'),('Equipo deportivo'),('Material de laboratorio'),
('Diplomas y certificados'),('Insumos de limpieza'),('Equipo de computo'),
('Material de oficina'),('Uniforme escolar'),('Instrumentos musicales'),
('Material de arte'),('Medicinas para topico'),('Alimentos para cafeteria');

-- ================================================================
-- PROCEDIMIENTO PRINCIPAL
-- ================================================================
DELIMITER $$

DROP PROCEDURE IF EXISTS SeedData$$
CREATE PROCEDURE SeedData()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_grado_num, v_i, v_j, v_student_count, v_worker_count, v_parent_count INT DEFAULT 0;
    DECLARE v_externo_start, v_externo_count INT DEFAULT 0;
    DECLARE v_nivel VARCHAR(50);
    DECLARE v_seccion CHAR(1);
    DECLARE v_nombre, v_apellido, v_cargo, v_parentesco VARCHAR(100);
    DECLARE v_last_id, v_dni_counter INT;
    DECLARE v_estudiante_actual, v_padre_actual, v_id_alumna_rand INT;
    DECLARE v_parent_idx INT;
    DECLARE v_date DATE;
    DECLARE v_end_date DATE;
    DECLARE v_base_num INT DEFAULT 60;

    -- Cursores
    DECLARE cur_grado CURSOR FOR SELECT grado_num, nivel, seccion FROM temp_grados;
    DECLARE cur_alumna CURSOR FOR SELECT id_persona FROM alumna ORDER BY id_persona;
    DECLARE cur_cargo CURSOR FOR SELECT cargo FROM temp_cargos;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    SELECT '>>> Iniciando generacion de datos...' AS log;

    -- ============================================================
    -- 1. ALUMNAS (~506 estudiantes)
    -- ============================================================
    SELECT '>>> Generando alumnas...' AS log;

    SET v_student_count = 0;
    OPEN cur_grado;

    read_grado: LOOP
        FETCH cur_grado INTO v_grado_num, v_nivel, v_seccion;
        IF done THEN LEAVE read_grado; END IF;

        SET v_i = 1;
        WHILE v_i <= 23 DO
            SELECT nombre INTO v_nombre FROM temp_nombres_f ORDER BY RAND() LIMIT 1;
            SELECT apellido INTO v_apellido FROM temp_apellidos ORDER BY RAND() LIMIT 1;

            INSERT INTO persona (nombre, apellido, dni) VALUES (v_nombre, v_apellido, NULL);
            SET v_last_id = LAST_INSERT_ID();

            INSERT INTO alumna (id_persona, grado, nivel, seccion)
            VALUES (v_last_id, CONCAT(v_grado_num, '°'), v_nivel, v_seccion);

            SET v_i = v_i + 1;
            SET v_student_count = v_student_count + 1;
        END WHILE;
    END LOOP;
    CLOSE cur_grado;
    SET done = 0;

    SELECT CONCAT('    Alumnas generadas: ', v_student_count) AS log;

    -- ============================================================
    -- 2. TRABAJADORES (~60)
    -- ============================================================
    SELECT '>>> Generando trabajadores...' AS log;

    SET v_worker_count = 0;
    SET v_dni_counter = 70000001;
    OPEN cur_cargo;

    read_cargo: LOOP
        FETCH cur_cargo INTO v_cargo;
        IF done THEN LEAVE read_cargo; END IF;

        -- ~2 personas por cargo (para algunos cargos), asegurando ~60 total
        SET v_j = 1;
        WHILE v_j <= 2 DO
            SELECT nombre INTO v_nombre FROM temp_nombres_m ORDER BY RAND() LIMIT 1;
            SELECT apellido INTO v_apellido FROM temp_apellidos ORDER BY RAND() LIMIT 1;

            INSERT INTO persona (nombre, apellido, dni)
            VALUES (v_nombre, v_apellido, CAST(v_dni_counter AS CHAR));
            SET v_last_id = LAST_INSERT_ID();

            INSERT INTO trabajador (id_persona, cargo) VALUES (v_last_id, v_cargo);

            SET v_dni_counter = v_dni_counter + 1;
            SET v_worker_count = v_worker_count + 1;
            SET v_j = v_j + 1;
        END WHILE;
    END LOOP;
    CLOSE cur_cargo;
    SET done = 0;

    SELECT CONCAT('    Trabajadores generados: ', v_worker_count) AS log;

    -- ============================================================
    -- 3. PADRES / APODERADOS (~280)
    -- ============================================================
    SELECT '>>> Generando padres/apoderados...' AS log;

    SET v_parent_count = 0;
    SET v_dni_counter = 10000001;

    -- Generar ~280 padres: ~140 hombres y ~140 mujeres
    SET v_i = 1;
    WHILE v_i <= 140 DO
        -- Padre (masculino)
        SELECT nombre INTO v_nombre FROM temp_nombres_m ORDER BY RAND() LIMIT 1;
        SELECT apellido INTO v_apellido FROM temp_apellidos ORDER BY RAND() LIMIT 1;

        INSERT INTO persona (nombre, apellido, dni)
        VALUES (v_nombre, v_apellido,
                IF(RAND() > 0.25, CAST(v_dni_counter AS CHAR), NULL));
        SET v_last_id = LAST_INSERT_ID();
        INSERT INTO padre_apoderado (id_persona) VALUES (v_last_id);

        SET v_dni_counter = v_dni_counter + 1;
        SET v_parent_count = v_parent_count + 1;

        -- Madre (femenino)
        SELECT nombre INTO v_nombre FROM temp_nombres_f ORDER BY RAND() LIMIT 1;
        SELECT apellido INTO v_apellido FROM temp_apellidos ORDER BY RAND() LIMIT 1;

        INSERT INTO persona (nombre, apellido, dni)
        VALUES (v_nombre, v_apellido,
                IF(RAND() > 0.30, CAST(v_dni_counter AS CHAR), NULL));
        SET v_last_id = LAST_INSERT_ID();
        INSERT INTO padre_apoderado (id_persona) VALUES (v_last_id);

        SET v_dni_counter = v_dni_counter + 1;
        SET v_parent_count = v_parent_count + 1;

        SET v_i = v_i + 1;
    END WHILE;

    SELECT CONCAT('    Padres generados: ', v_parent_count) AS log;

    -- ============================================================
    -- 4. PERSONAS EXTERNAS (~20)
    -- ============================================================
    SELECT '>>> Generando personas externas...' AS log;

    SET v_dni_counter = 20000001;
    SET v_externo_count = 0;
    SET v_i = 1;
    WHILE v_i <= 20 DO
        SELECT nombre INTO v_nombre FROM temp_nombres_m ORDER BY RAND() LIMIT 1;
        SELECT apellido INTO v_apellido FROM temp_apellidos ORDER BY RAND() LIMIT 1;

        INSERT INTO persona (nombre, apellido, dni)
        VALUES (v_nombre, v_apellido, CAST(v_dni_counter AS CHAR));
        SET v_dni_counter = v_dni_counter + 1;
        SET v_externo_count = v_externo_count + 1;
        SET v_i = v_i + 1;
    END WHILE;

    SET v_i = 1;
    WHILE v_i <= 5 DO
        SELECT nombre INTO v_nombre FROM temp_nombres_f ORDER BY RAND() LIMIT 1;
        SELECT apellido INTO v_apellido FROM temp_apellidos ORDER BY RAND() LIMIT 1;

        INSERT INTO persona (nombre, apellido, dni)
        VALUES (v_nombre, v_apellido, CAST(v_dni_counter AS CHAR));
        SET v_dni_counter = v_dni_counter + 1;
        SET v_externo_count = v_externo_count + 1;
        SET v_i = v_i + 1;
    END WHILE;

    SELECT CONCAT('    Personas externas generadas: ', v_externo_count) AS log;

    -- ============================================================
    -- 5. RELACIONES PADRE_ALUMNA (~760)
    -- ============================================================
    SELECT '>>> Generando relaciones padre-alumna...' AS log;

    -- Estrategia: cada padre se asigna a 2-3 alumnas (genera el efecto hermanas)
    SET v_parent_idx = 0;
    OPEN cur_alumna;

    read_alumna: LOOP
        FETCH cur_alumna INTO v_estudiante_actual;
        IF done THEN LEAVE read_alumna; END IF;

        -- Primer padre (obligatorio, todos tienen al menos 1)
        SET v_padre_actual = (
            SELECT MIN(id_persona) FROM padre_apoderado
            WHERE id_persona > 0
        ) + v_parent_idx;

        IF v_padre_actual > (SELECT MAX(id_persona) FROM padre_apoderado) THEN
            SET v_parent_idx = 0;
            SET v_padre_actual = (SELECT MIN(id_persona) FROM padre_apoderado);
        END IF;

        SELECT parentesco INTO v_parentesco FROM temp_parentescos ORDER BY RAND() LIMIT 1;

        INSERT INTO padre_alumna (id_padre, id_alumna, parentesco, es_tutor_principal)
        VALUES (v_padre_actual, v_estudiante_actual, v_parentesco, TRUE);

        SET v_parent_idx = v_parent_idx + 1;

        -- Segundo padre (30% de probabilidad)
        IF RAND() < 0.30 THEN
            SET v_padre_actual = (
                SELECT MIN(id_persona) FROM padre_apoderado
                WHERE id_persona > 0
            ) + v_parent_idx;

            IF v_padre_actual > (SELECT MAX(id_persona) FROM padre_apoderado) THEN
                SET v_parent_idx = 0;
                SET v_padre_actual = (SELECT MIN(id_persona) FROM padre_apoderado);
            END IF;

            SELECT parentesco INTO v_parentesco FROM temp_parentescos ORDER BY RAND() LIMIT 1;

            INSERT INTO padre_alumna (id_padre, id_alumna, parentesco, es_tutor_principal)
            VALUES (v_padre_actual, v_estudiante_actual, v_parentesco, FALSE);

            SET v_parent_idx = v_parent_idx + 1;
        END IF;
    END LOOP;
    CLOSE cur_alumna;
    SET done = 0;

    SELECT CONCAT('    Relaciones padre-alumna generadas') AS log;

    -- ============================================================
    -- 6. USUARIOS (5)
    -- ============================================================
    SELECT '>>> Generando usuarios...' AS log;

    -- Contraseñas bcrypt reales (10 rounds):
    --   admin       -> admin123
    --   demas users -> trabajador123

    INSERT INTO usuario (usuario, clave, estado, id_rol)
    VALUES ('admin', '$2b$10$KfE4HRhsAKpDZiEbXepuoe34VuaRiKu2o3YVkbZ9rBkl02fjpmLj.', TRUE,
            (SELECT id_rol FROM rol WHERE nombre_rol = 'ADMIN'));

    INSERT INTO usuario (usuario, clave, estado, id_rol)
    VALUES ('jperez', '$2b$10$YU4s1KFmNKVVqKZLfdkPvuFs3oxidAc.Zr2SvGal6JgrUNCPqs5dW', TRUE,
            (SELECT id_rol FROM rol WHERE nombre_rol = 'USER'));

    INSERT INTO usuario (usuario, clave, estado, id_rol)
    VALUES ('mgarcia', '$2b$10$YU4s1KFmNKVVqKZLfdkPvuFs3oxidAc.Zr2SvGal6JgrUNCPqs5dW', TRUE,
            (SELECT id_rol FROM rol WHERE nombre_rol = 'USER'));

    INSERT INTO usuario (usuario, clave, estado, id_rol)
    VALUES ('rlopez', '$2b$10$YU4s1KFmNKVVqKZLfdkPvuFs3oxidAc.Zr2SvGal6JgrUNCPqs5dW', TRUE,
            (SELECT id_rol FROM rol WHERE nombre_rol = 'USER'));

    INSERT INTO usuario (usuario, clave, estado, id_rol)
    VALUES ('sflores', '$2b$10$YU4s1KFmNKVVqKZLfdkPvuFs3oxidAc.Zr2SvGal6JgrUNCPqs5dW', FALSE,
            (SELECT id_rol FROM rol WHERE nombre_rol = 'USER'));

    SELECT '    Usuarios generados: 5 (admin + 4 users)' AS log;

    -- ============================================================
    -- 7. REGISTROS DE INGRESO (~6 meses, ~11,700 registros)
    -- ============================================================
    SELECT '>>> Generando registros de ingreso (6 meses)...' AS log;

    SET v_date = DATE_SUB(CURDATE(), INTERVAL 180 DAY);
    SET v_end_date = CURDATE();

    WHILE v_date <= v_end_date DO
        -- 7a. Trabajadores: los 60 entran cada dia
        INSERT INTO ingreso_personal (id_persona, id_alumna, fecha, hora_ingreso, hora_salida, motivo, numero_registro)
        SELECT
            t.id_persona,
            NULL,
            v_date,
            ADDTIME('07:00:00', SEC_TO_TIME(FLOOR(RAND() * 5400))),
            CASE WHEN RAND() > 0.08 THEN ADDTIME('14:00:00', SEC_TO_TIME(FLOOR(RAND() * 14400))) ELSE NULL END,
            NULL,
            ROW_NUMBER() OVER (ORDER BY t.id_persona)
        FROM trabajador t;

        -- 7b. Padres y externos: ~5 al dia
        INSERT INTO ingreso_personal (id_persona, id_alumna, fecha, hora_ingreso, hora_salida, motivo, numero_registro)
        SELECT
            p.id_persona,
            NULL,
            v_date,
            ADDTIME('08:00:00', SEC_TO_TIME(FLOOR(RAND() * 21600))),
            CASE WHEN RAND() > 0.20 THEN ADDTIME('09:30:00', SEC_TO_TIME(FLOOR(RAND() * 10800))) ELSE NULL END,
            (SELECT motivo FROM temp_motivos_ingreso ORDER BY RAND() LIMIT 1),
            ROW_NUMBER() OVER (ORDER BY RAND()) + v_base_num
        FROM persona p
        WHERE NOT EXISTS (SELECT 1 FROM trabajador t WHERE t.id_persona = p.id_persona)
          AND NOT EXISTS (SELECT 1 FROM alumna a WHERE a.id_persona = p.id_persona)
        ORDER BY RAND()
        LIMIT 5;

        SET v_date = DATE_ADD(v_date, INTERVAL 1 DAY);
    END WHILE;

    SELECT CONCAT('    Registros de ingreso generados para 180 dias') AS log;

    -- ============================================================
    -- 8. SALIDAS DE ALUMNAS (~1,800)
    -- ============================================================
    SELECT '>>> Generando salidas de alumnas...' AS log;

    SET v_date = DATE_SUB(CURDATE(), INTERVAL 180 DAY);

    WHILE v_date <= v_end_date DO
        INSERT INTO salidas (id_alumna, motivo, personaquerecoje, dni, telefono, fecha, hora_salida, hora_retorno, numero_registro)
        SELECT
            a.id_persona,
            (SELECT motivo FROM temp_motivos_salida ORDER BY RAND() LIMIT 1),
            (SELECT nombre FROM temp_nombres_m ORDER BY RAND() LIMIT 1),
            CAST(FLOOR(10000000 + RAND() * 90000000) AS CHAR),
            CAST(FLOOR(900000000 + RAND() * 100000000) AS CHAR),
            v_date,
            ADDTIME('09:00:00', SEC_TO_TIME(FLOOR(RAND() * 18000))),
            CASE WHEN RAND() > 0.5 THEN ADDTIME('11:00:00', SEC_TO_TIME(FLOOR(RAND() * 14400))) ELSE NULL END,
            ROW_NUMBER() OVER (ORDER BY RAND())
        FROM alumna a
        ORDER BY RAND()
        LIMIT 10;

        SET v_date = DATE_ADD(v_date, INTERVAL 1 DAY);
    END WHILE;

    SELECT CONCAT('    Salidas generadas: ~', 180 * 10) AS log;

    -- ============================================================
    -- 9. ENCARGOS (~50)
    -- ============================================================
    SELECT '>>> Generando encargos...' AS log;

    SET v_i = 1;
    WHILE v_i <= 50 DO
        INSERT INTO encargo (fecha_enc, encargo, numero_registro)
        VALUES (
            DATE_SUB(CURDATE(), INTERVAL FLOOR(RAND() * 180) DAY),
            (SELECT encargo FROM temp_encargos ORDER BY RAND() LIMIT 1),
            v_i
        );
        SET v_i = v_i + 1;
    END WHILE;

    SELECT CONCAT('    Encargos generados: 50') AS log;
    SELECT '>>> GENERACION DE DATOS COMPLETADA <<<' AS log;

END$$

DELIMITER ;

-- ============================================================
-- EJECUTAR EL PROCEDIMIENTO
-- ============================================================
CALL SeedData();
DROP PROCEDURE IF EXISTS SeedData;

-- ============================================================
-- LIMPIEZA DE TABLAS TEMPORALES
-- ============================================================
DROP TEMPORARY TABLE IF EXISTS temp_nombres_f;
DROP TEMPORARY TABLE IF EXISTS temp_nombres_m;
DROP TEMPORARY TABLE IF EXISTS temp_apellidos;
DROP TEMPORARY TABLE IF EXISTS temp_grados;
DROP TEMPORARY TABLE IF EXISTS temp_cargos;
DROP TEMPORARY TABLE IF EXISTS temp_motivos_salida;
DROP TEMPORARY TABLE IF EXISTS temp_motivos_ingreso;
DROP TEMPORARY TABLE IF EXISTS temp_parentescos;
DROP TEMPORARY TABLE IF EXISTS temp_encargos;

-- ============================================================
-- RESUMEN FINAL
-- ============================================================
SELECT '========================================' AS '';
SELECT '  RESUMEN DE DATOS GENERADOS' AS '';
SELECT '========================================' AS '';
SELECT CONCAT('  Personas totales:        ', COUNT(*)) AS '' FROM persona;
SELECT CONCAT('  Alumnas:                 ', COUNT(*)) AS '' FROM alumna;
SELECT CONCAT('  Trabajadores:            ', COUNT(*)) AS '' FROM trabajador;
SELECT CONCAT('  Padres/Apoderados:       ', COUNT(*)) AS '' FROM padre_apoderado;
SELECT CONCAT('  Externos:                ', (SELECT COUNT(*) FROM persona p WHERE NOT EXISTS (SELECT 1 FROM trabajador t WHERE t.id_persona = p.id_persona) AND NOT EXISTS (SELECT 1 FROM alumna a WHERE a.id_persona = p.id_persona) AND NOT EXISTS (SELECT 1 FROM padre_apoderado pa WHERE pa.id_persona = p.id_persona))) AS '';
SELECT CONCAT('  Relaciones padre-alumna: ', COUNT(*)) AS '' FROM padre_alumna;
SELECT CONCAT('  Registros de ingreso:    ', COUNT(*)) AS '' FROM ingreso_personal;
SELECT CONCAT('  Salidas de alumnas:      ', COUNT(*)) AS '' FROM salidas;
SELECT CONCAT('  Encargos:                ', COUNT(*)) AS '' FROM encargo;
SELECT CONCAT('  Usuarios:                ', COUNT(*)) AS '' FROM usuario;
SELECT '========================================' AS '';
