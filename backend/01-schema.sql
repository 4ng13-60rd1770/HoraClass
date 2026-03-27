
-- CAMBIAR A ESTADOUSUARIO
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
-- DATOS INICIALES (Catálogos básicos)ESTADOUSUARIO
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

-- =========================
-- TABLA ESTADO_GRUPO (Catálogo)
-- =========================
CREATE TABLE ESTADO_GRUPO (
    IdEstadoGrupo INT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL
);

-- =========================
-- DATOS INICIALES
-- =========================
INSERT INTO ESTADO_GRUPO (IdEstadoGrupo, Nombre) VALUES 
(1, 'ACTIVO'),
(2, 'CERRADO'),
(3, 'CANCELADO')
ON CONFLICT (IdEstadoGrupo) DO NOTHING;
--===========
--Profesor
--===========

CREATE TABLE PROFESOR (
    IdProfesor BIGINT PRIMARY KEY,     -- ← PK
    Departamento VARCHAR(100),
    Especialidad VARCHAR(100),
    CargaHoras INT NOT NULL,
    Escalafon VARCHAR(50),
    FOREIGN KEY (IdProfesor) REFERENCES USUARIO(idUsuario)  -- ← FK a la vez
);

--========
--Grupos
--========
CREATE TABLE GRUPO (
    IdGrupo VARCHAR(20) PRIMARY KEY,   
    IdCurso INT NOT NULL,
    IdProfesor BIGINT NOT NULL,
    CupoMaximo INT NOT NULL CHECK (CupoMaximo >= 10),
    CupoMinimo INT NOT NULL CHECK (CupoMinimo >= 1 AND CupoMinimo <= CupoMaximo),
    IdEstadoGrupo INT NOT NULL,
    FOREIGN KEY (IdCurso) REFERENCES CURSO(IdCurso),
    FOREIGN KEY (IdProfesor) REFERENCES PROFESOR(IdProfesor),
    FOREIGN KEY (IdEstadoGrupo) REFERENCES ESTADO_GRUPO(IdEstadoGrupo)
);
