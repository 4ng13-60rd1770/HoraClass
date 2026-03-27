
CREATE TABLE ESTADO (
    idEstado INT PRIMARY KEY,
    nombre VARCHAR(50)
);

-- =========================
-- TABLA USUARIO 
-- =========================
CREATE TABLE USUARIO (
    idUsuario BIGINT PRIMARY KEY,    -- DNI como identificador único
    primerNombre VARCHAR(50) NOT NULL,
    segundoNombre VARCHAR(50),
    primerApellido VARCHAR(50) NOT NULL,
    segundoApellido VARCHAR(50),
    username VARCHAR(50) UNIQUE NOT NULL, -- Para login
    password VARCHAR(255) NOT NULL,       -- Encriptada (BCrypt)
    rol VARCHAR(50) NOT NULL DEFAULT 'ADMIN',
    idEstado INT,
    FOREIGN KEY (idEstado) REFERENCES ESTADO(idEstado)
);



-- =========================
-- DATOS INICIALES (Catálogos básicos)
-- =========================
INSERT INTO ESTADO (idEstado, nombre) VALUES 
(1, 'ACTIVO'),
(2, 'INACTIVO'),
(3, 'SUSPENDIDO')
ON CONFLICT (idEstado) DO NOTHING;


-- CURSO
--============================
CREATE TABLE MODALIDAD (
    IdModalidad INT PRIMARY KEY,
    Nombre VARCHAR(100)
);

CREATE TABLE TIPOCURSO (
    IdTipoCurso INT PRIMARY KEY,
    Nombre VARCHAR(100)
);

CREATE TABLE CURSO (
    IdCurso INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Semestre INT,
    Creditos INT,
    IdModalidad INT,
    IdTipoCurso INT,
    FOREIGN KEY (IdModalidad) REFERENCES MODALIDAD(IdModalidad),
    FOREIGN KEY (IdTipoCurso) REFERENCES TIPOCURSO(IdTipoCurso)
);

-- Catálogo TIPO_CURSO
INSERT INTO TIPOCURSO (IdTipoCurso, Nombre) VALUES
(1, 'Teórico'),
(2, 'Práctico'),
(3, 'Teórico Practico')
(4, 'Seminario'),
(6, 'Taller'),
(7, 'Proyecto'),
(8, 'Laboratorio');

-- Catálogo MODALIDAD
INSERT INTO MODALIDAD (IdModalidad, Nombre) VALUES
(1, 'Presencial'),
(2, 'Virtual'),
(3, 'Híbrida');

-- Curso de ejemplo: "Programación Avanzada"
-- Es Práctico (necesita lab) + Presencial
INSERT INTO CURSO (IdCurso, Nombre, Semestre, Creditos, IdModalidad, IdTipoCurso) VALUES
(101, 'Programación Avanzada', 5, 4, 1, 2);  -- Modalidad=Presencial, Tipo=Práctico

-- Curso de ejemplo: "Ingeniería de Software"
-- Es Teórico (aula normal) + Híbrida
INSERT INTO CURSO (IdCurso, Nombre, Semestre, Creditos, IdModalidad, IdTipoCurso) VALUES
(102, 'Ingeniería de Software', 6, 3, 3, 1);  -- Modalidad=Híbrida, Tipo=Teórico



