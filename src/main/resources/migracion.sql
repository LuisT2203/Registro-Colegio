-- ============================================================
-- MIGRACION: Normalizacion de BD Registro Colegio
-- Ejecutar con la aplicacion DETENIDA
-- Luego desplegar con spring.jpa.hibernate.ddl-auto=update
-- ============================================================

-- PASO 1: Crear tabla ROL e insertar roles
CREATE TABLE IF NOT EXISTS rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

INSERT IGNORE INTO rol (nombre_rol) VALUES ('ADMIN');
INSERT IGNORE INTO rol (nombre_rol) VALUES ('USER');

-- PASO 2: Crear tabla PERSONA
CREATE TABLE IF NOT EXISTS persona (
    id_persona INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE,
    UNIQUE KEY uk_dni (dni)
);

-- PASO 3: Crear tabla TRABAJADOR
CREATE TABLE IF NOT EXISTS trabajador (
    id_persona INT PRIMARY KEY,
    cargo VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona(id_persona) ON DELETE CASCADE
);

-- PASO 4: Crear tabla ALUMNA
CREATE TABLE IF NOT EXISTS alumna (
    id_persona INT PRIMARY KEY,
    grado VARCHAR(20) NOT NULL,
    nivel VARCHAR(50) NOT NULL,
    seccion VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona(id_persona) ON DELETE CASCADE
);

-- PASO 5: Crear tabla PADRE_APODERADO
CREATE TABLE IF NOT EXISTS padre_apoderado (
    id_persona INT PRIMARY KEY,
    FOREIGN KEY (id_persona) REFERENCES persona(id_persona) ON DELETE CASCADE
);

-- PASO 6: Crear tabla PADRE_ALUMNA
CREATE TABLE IF NOT EXISTS padre_alumna (
    id_padre INT NOT NULL,
    id_alumna INT NOT NULL,
    parentesco VARCHAR(50) NOT NULL,
    es_tutor_principal BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id_padre, id_alumna),
    FOREIGN KEY (id_padre) REFERENCES padre_apoderado(id_persona) ON DELETE CASCADE,
    FOREIGN KEY (id_alumna) REFERENCES alumna(id_persona) ON DELETE CASCADE
);

-- ============================================================
-- PASO 7: MIGRAR PersonalColegio -> persona + trabajador
-- ============================================================
INSERT INTO persona (nombre, apellido, dni)
SELECT nombre_personal, apellido_personal, NULL
FROM PersonalColegio;

INSERT INTO trabajador (id_persona, cargo)
SELECT p.id_persona, pc.cargo_personal
FROM PersonalColegio pc
JOIN persona p ON p.nombre = pc.nombre_personal AND p.apellido = pc.apellido_personal
WHERE p.dni IS NULL;

-- ============================================================
-- PASO 8: MIGRAR PersonaExterna -> persona (sin subtabla = externo)
-- ============================================================
INSERT INTO persona (nombre, apellido, dni)
SELECT nombre_personaE, apellido_personaE, CAST(dni AS CHAR)
FROM PersonaExterna;

-- ============================================================
-- PASO 9: MIGRAR PPFF -> persona + alumna + persona + padre + padre_alumna
-- ============================================================
-- 9a. Insertar ALUMNAS (sin DNI)
INSERT IGNORE INTO persona (nombre, apellido, dni)
SELECT DISTINCT nombre_alu, apellido_alu, NULL
FROM ppff;

INSERT IGNORE INTO alumna (id_persona, grado, nivel, seccion)
SELECT p.id_persona, pf.anio_alu, '', ''
FROM ppff pf
JOIN persona p ON p.nombre = pf.nombre_alu AND p.apellido = pf.apellido_alu AND p.dni IS NULL;

-- 9b. Insertar PADRES
INSERT IGNORE INTO persona (nombre, apellido, dni)
SELECT DISTINCT nombre_ppff, apellido_ppff, dni
FROM ppff
WHERE dni IS NOT NULL AND dni != '';

INSERT IGNORE INTO persona (nombre, apellido, dni)
SELECT DISTINCT nombre_ppff, apellido_ppff, NULL
FROM ppff
WHERE dni IS NULL OR dni = '';

INSERT IGNORE INTO padre_apoderado (id_persona)
SELECT p.id_persona
FROM ppff pf
JOIN persona p ON p.nombre = pf.nombre_ppff AND p.apellido = pf.apellido_ppff
LEFT JOIN padre_apoderado pa ON pa.id_persona = p.id_persona
WHERE pa.id_persona IS NULL;

-- 9c. Insertar relaciones PADRE_ALUMNA
INSERT IGNORE INTO padre_alumna (id_padre, id_alumna, parentesco, es_tutor_principal)
SELECT pa.id_persona, al.id_persona, 'Padre/Madre', TRUE
FROM ppff pf
JOIN persona pp ON pp.nombre = pf.nombre_ppff AND pp.apellido = pf.apellido_ppff
JOIN persona ap ON ap.nombre = pf.nombre_alu AND ap.apellido = pf.apellido_alu AND ap.dni IS NULL
JOIN padre_apoderado pa ON pa.id_persona = pp.id_persona
JOIN alumna al ON al.id_persona = ap.id_persona;

-- ============================================================
-- PASO 10: MIGRAR INGRESOS
-- ============================================================
-- 10a. Renombrar tablas viejas de ingreso
RENAME TABLE ingreso_personal TO ingreso_personal_old;
RENAME TABLE ingreso_ppff TO ingreso_ppff_old;
RENAME TABLE ingreso_personaE TO ingreso_personaE_old;

-- 10b. Crear nueva tabla ingreso_personal
CREATE TABLE ingreso_personal (
    id_ingreso INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT NOT NULL,
    id_alumna INT NULL,
    fecha DATE NOT NULL,
    hora_ingreso TIME NOT NULL,
    hora_salida TIME NULL,
    motivo VARCHAR(255) NULL,
    numero_registro INT NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES persona(id_persona),
    FOREIGN KEY (id_alumna) REFERENCES alumna(id_persona)
);

-- 10c. Migrar ingresos de personal (trabajadores)
INSERT INTO ingreso_personal (id_ingreso, id_persona, fecha, hora_ingreso, hora_salida, motivo, numero_registro)
SELECT
    ipc.id_ingresoPersonal,
    (SELECT t.id_persona FROM trabajador t
     JOIN persona p ON p.id_persona = t.id_persona
     JOIN PersonalColegio pc ON pc.nombre_personal = p.nombre AND pc.apellido_personal = p.apellido
     WHERE pc.id_personal = ipc.id_personal LIMIT 1),
    ipc.fecha,
    ipc.hora_ingreso,
    ipc.hora_salida,
    NULL,
    ipc.numeroRegistro
FROM ingreso_personal_old ipc;

-- 10d. Migrar ingresos de padres (PPFF)
INSERT INTO ingreso_personal (id_ingreso, id_persona, fecha, hora_ingreso, hora_salida, motivo, numero_registro)
SELECT
    ipp.id_ingresoPPFF,
    (SELECT pa.id_persona FROM padre_apoderado pa
     JOIN persona p ON p.id_persona = pa.id_persona
     JOIN ppff pf ON pf.nombre_ppff = p.nombre AND pf.apellido_ppff = p.apellido
     WHERE pf.id_ppff = ipp.id_ppff LIMIT 1),
    ipp.fecha,
    ipp.hora_ingreso,
    ipp.hora_salida,
    ipp.asunto,
    ipp.numeroRegistro
FROM ingreso_ppff_old ipp;

-- 10e. Migrar ingresos de personas externas
INSERT INTO ingreso_personal (id_ingreso, id_persona, fecha, hora_ingreso, hora_salida, motivo, numero_registro)
SELECT
    ipe.id_ingresoPersonaE,
    (SELECT p.id_persona FROM persona p
     JOIN PersonaExterna pe ON pe.nombre_personaE = p.nombre AND pe.apellido_personaE = p.apellido
     WHERE pe.id_personaE = ipe.id_personaE LIMIT 1),
    ipe.fecha,
    ipe.hora_ingreso,
    ipe.hora_salida,
    ipe.asunto,
    ipe.numeroRegistro
FROM ingreso_personaE_old ipe;

-- ============================================================
-- PASO 11: ACTUALIZAR usuario para usar FK a rol
-- ============================================================
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS id_rol INT;
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS estado BOOLEAN DEFAULT TRUE;

UPDATE usuario SET id_rol = (SELECT id_rol FROM rol WHERE nombre_rol = 'ADMIN')
WHERE tipo = 'admin';
UPDATE usuario SET id_rol = (SELECT id_rol FROM rol WHERE nombre_rol = 'USER')
WHERE tipo = 'user' OR tipo != 'admin' OR id_rol IS NULL;

ALTER TABLE usuario ADD FOREIGN KEY fk_usuario_rol (id_rol) REFERENCES rol(id_rol);

-- ============================================================
-- PASO 12: ACTUALIZAR tabla salidas para FK a alumna
-- ============================================================
ALTER TABLE salidas ADD COLUMN IF NOT EXISTS id_alumna INT;

UPDATE salidas s
JOIN persona p ON p.nombre = s.nombre_alu AND p.apellido = s.nombre_alu
SET s.id_alumna = (SELECT al.id_persona FROM alumna al WHERE al.id_persona = p.id_persona LIMIT 1)
WHERE s.id_alumna IS NULL;

-- ============================================================
-- PASO 12b: AGREGAR FK id_padre a salidas
-- ============================================================
ALTER TABLE salidas ADD COLUMN IF NOT EXISTS id_padre INT NULL;
ALTER TABLE salidas ADD FOREIGN KEY IF NOT EXISTS fk_salidas_padre (id_padre) REFERENCES padre_apoderado(id_persona);

-- ============================================================
-- PASO 13: ELIMINAR tablas viejas (ejecutar solo despues de verificar)
-- ============================================================
-- DROP TABLE IF EXISTS ingreso_personal_old;
-- DROP TABLE IF EXISTS ingreso_ppff_old;
-- DROP TABLE IF EXISTS ingreso_personaE_old;
-- DROP TABLE IF EXISTS PersonalColegio;
-- DROP TABLE IF EXISTS ppff;
-- DROP TABLE IF EXISTS PersonaExterna;
-- ALTER TABLE usuario DROP COLUMN IF EXISTS tipo;

