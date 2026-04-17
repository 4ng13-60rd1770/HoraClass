CREATE TABLE TIPO_DOCUMENTO (
    IdTipoDocumento     INT         PRIMARY KEY,
    Nombre              VARCHAR(50) NOT NULL
);

CREATE TABLE ESTADO (
    IdEstado            INT         PRIMARY KEY,
    Nombre              VARCHAR(50) NOT NULL
);

CREATE TABLE DIA (
    IdDia               INT         PRIMARY KEY,
    Nombre              VARCHAR(20) NOT NULL
);

CREATE TABLE PERIODO (
    IdPeriodo           INT         PRIMARY KEY,
    Anio                INT         NOT NULL,
    Semestre            TINYINT     NOT NULL,
    Fecha_inicio        DATE        NOT NULL,
    Fecha_fin           DATE        NOT NULL,
    Activo              BIT         NOT NULL DEFAULT 0,

    CONSTRAINT uq_periodo UNIQUE (Anio, Semestre)
);

CREATE TABLE PARAMETRO_PERIODO (
    IdPeriodo               INT     PRIMARY KEY,
    Cupo_maximo             INT     NOT NULL DEFAULT 40,
    Tolerancia_pct          INT     NOT NULL DEFAULT 10,
    Cupo_minimo_cierre      INT     NOT NULL DEFAULT 10,
    Max_sesiones_semana     INT     NOT NULL DEFAULT 4,
    Hora_inicio_franja      TIME    NOT NULL DEFAULT '07:00:00',
    Hora_fin_franja_lv      TIME    NOT NULL DEFAULT '22:00:00',
    Hora_fin_franja_sa      TIME    NOT NULL DEFAULT '13:00:00',
    Hora_inicio_almuerzo    TIME    NOT NULL DEFAULT '12:00:00',
    Hora_fin_almuerzo       TIME    NOT NULL DEFAULT '13:00:00',
    Duracion_sesion_horas   INT     NOT NULL DEFAULT 2,

    FOREIGN KEY (IdPeriodo) REFERENCES PERIODO(IdPeriodo)
);

CREATE TABLE DEPARTAMENTO (
    IdDepartamento      INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE EMPLEADO (
    IdEmpleado          INT         PRIMARY KEY,
    IdTipoDocumento     INT         NOT NULL,
    Documento           VARCHAR(20) NOT NULL,
    Primer_nombre       VARCHAR(50) NOT NULL,
    Segundo_nombre      VARCHAR(50),
    Primer_apellido     VARCHAR(50) NOT NULL,
    Segundo_apellido    VARCHAR(50),
    IdEstado            INT         NOT NULL,

    CONSTRAINT uq_empleado_doc UNIQUE (IdTipoDocumento, Documento),
    FOREIGN KEY (IdTipoDocumento)   REFERENCES TIPO_DOCUMENTO(IdTipoDocumento),
    FOREIGN KEY (IdEstado)          REFERENCES ESTADO(IdEstado)
);

CREATE TABLE ESTUDIANTE (
    IdEstudiante        INT         PRIMARY KEY,
    IdTipoDocumento     INT         NOT NULL,
    Documento           VARCHAR(20) NOT NULL,
    Primer_nombre       VARCHAR(50) NOT NULL,
    Segundo_nombre      VARCHAR(50),
    Primer_apellido     VARCHAR(50) NOT NULL,
    Segundo_apellido    VARCHAR(50),
    IdEstado            INT         NOT NULL,

    CONSTRAINT uq_estudiante_doc UNIQUE (IdTipoDocumento, Documento),
    FOREIGN KEY (IdTipoDocumento)   REFERENCES TIPO_DOCUMENTO(IdTipoDocumento),
    FOREIGN KEY (IdEstado)          REFERENCES ESTADO(IdEstado)
);

CREATE TABLE NIVEL_ACADEMICO (
    IdNivel             INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE INSTITUCION (
    IdInstitucion       INT         PRIMARY KEY,
    Nombre              VARCHAR(200) NOT NULL
);

CREATE TABLE AREA_CONOCIMIENTO (
    IdArea              INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE ESTUDIO_EMPLEADO (
    IdEstudio           INT         PRIMARY KEY,
    IdEmpleado          INT         NOT NULL,
    IdNivel             INT         NOT NULL,
    IdArea              INT         NOT NULL,
    IdInstitucion       INT         NOT NULL,
    Titulo              VARCHAR(200) NOT NULL,
    Anio_grado          INT,

    FOREIGN KEY (IdEmpleado)        REFERENCES EMPLEADO(IdEmpleado),
    FOREIGN KEY (IdNivel)           REFERENCES NIVEL_ACADEMICO(IdNivel),
    FOREIGN KEY (IdArea)            REFERENCES AREA_CONOCIMIENTO(IdArea),
    FOREIGN KEY (IdInstitucion)     REFERENCES INSTITUCION(IdInstitucion)
);

CREATE TABLE CARGO (
    IdCargo             INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE DIRECTIVO (
    IdEmpleado          INT         PRIMARY KEY,
    IdCargo             INT         NOT NULL,
    Fecha_inicio        DATE        NOT NULL,
    Fecha_fin           DATE,

    FOREIGN KEY (IdEmpleado)        REFERENCES EMPLEADO(IdEmpleado),
    FOREIGN KEY (IdCargo)           REFERENCES CARGO(IdCargo)
);

CREATE TABLE MODALIDAD_CONTRATO (
    IdModalidad         INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE ESCALAFON (
    IdEscalafon         INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE PROFESOR (
    IdEmpleado          INT         PRIMARY KEY,
    IdModalidad         INT         NOT NULL,
    IdEscalafon         INT         NOT NULL,
    Carga_horas         INT         NOT NULL,

    FOREIGN KEY (IdEmpleado)        REFERENCES EMPLEADO(IdEmpleado),
    FOREIGN KEY (IdModalidad)       REFERENCES MODALIDAD_CONTRATO(IdModalidad),
    FOREIGN KEY (IdEscalafon)       REFERENCES ESCALAFON(IdEscalafon)
);

CREATE TABLE RESTRICCION_PROFESOR (
    IdRestriccion       INT         PRIMARY KEY,
    IdEmpleado          INT         NOT NULL,
    IdDia               INT,
    Hora_inicio         TIME        NOT NULL,
    Hora_fin            TIME        NOT NULL,
    Descripcion         VARCHAR(200),

    FOREIGN KEY (IdEmpleado)        REFERENCES PROFESOR(IdEmpleado),
    FOREIGN KEY (IdDia)             REFERENCES DIA(IdDia)
);

CREATE TABLE PREGRADO (
    IdPregrado          INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL,
    IdDepartamento      INT         NOT NULL,

    FOREIGN KEY (IdDepartamento)    REFERENCES DEPARTAMENTO(IdDepartamento)
);

CREATE TABLE TIPO_CURSO (
    IdTipoCurso         INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE MODALIDAD_CURSO (
    IdModalidadCurso    INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE CURSO (
    IdCurso             INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL,
    Semestre_plan       TINYINT     NOT NULL,
    Creditos            INT         NOT NULL,
    IdTipoCurso         INT         NOT NULL,
    IdModalidadCurso    INT         NOT NULL,
    IdPregrado          INT         NOT NULL,

    FOREIGN KEY (IdTipoCurso)       REFERENCES TIPO_CURSO(IdTipoCurso),
    FOREIGN KEY (IdModalidadCurso)  REFERENCES MODALIDAD_CURSO(IdModalidadCurso),
    FOREIGN KEY (IdPregrado)        REFERENCES PREGRADO(IdPregrado)
);

CREATE TABLE PROFESOR_CURSO (
    IdEmpleado          INT,
    IdCurso             INT,
    PRIMARY KEY (IdEmpleado, IdCurso),

    FOREIGN KEY (IdEmpleado)        REFERENCES PROFESOR(IdEmpleado),
    FOREIGN KEY (IdCurso)           REFERENCES CURSO(IdCurso)
);

CREATE TABLE ESTADO_GRUPO (
    IdEstadoGrupo       INT         PRIMARY KEY,
    Nombre              VARCHAR(50) NOT NULL
);

CREATE TABLE GRUPO (
    IdGrupo             INT         PRIMARY KEY,
    IdCurso             INT         NOT NULL,
    IdPeriodo           INT         NOT NULL,
    IdEmpleado          INT         NOT NULL,
    Cupo_maximo         INT         NOT NULL,
    Cupo_minimo         INT         NOT NULL,
    IdEstadoGrupo       INT         NOT NULL,

    FOREIGN KEY (IdCurso)           REFERENCES CURSO(IdCurso),
    FOREIGN KEY (IdPeriodo)         REFERENCES PERIODO(IdPeriodo),
    FOREIGN KEY (IdEmpleado)        REFERENCES PROFESOR(IdEmpleado),
    FOREIGN KEY (IdEstadoGrupo)     REFERENCES ESTADO_GRUPO(IdEstadoGrupo)
);

CREATE TABLE TIPO_MATRICULA (
    IdTipoMatricula     INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE ESTADO_INSCRIPCION (
    IdEstadoInscripcion INT         PRIMARY KEY,
    Nombre              VARCHAR(50) NOT NULL
);

CREATE TABLE INSCRIPCION (
    IdEstudiante        INT,
    IdGrupo             INT,
    IdTipoMatricula     INT         NOT NULL,
    IdEstadoInscripcion INT         NOT NULL,
    Fecha_inscripcion   DATE        NOT NULL,
    Comentario          VARCHAR(255),

    PRIMARY KEY (IdEstudiante, IdGrupo),

    FOREIGN KEY (IdEstudiante)          REFERENCES ESTUDIANTE(IdEstudiante),
    FOREIGN KEY (IdGrupo)               REFERENCES GRUPO(IdGrupo),
    FOREIGN KEY (IdTipoMatricula)       REFERENCES TIPO_MATRICULA(IdTipoMatricula),
    FOREIGN KEY (IdEstadoInscripcion)   REFERENCES ESTADO_INSCRIPCION(IdEstadoInscripcion)
);

CREATE TABLE EDIFICIO (
    IdEdificio          INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE TIPO_AULA (
    IdTipoAula          INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE TIPO_SILLAS (
    IdTipoSillas        INT         PRIMARY KEY,
    Nombre              VARCHAR(100) NOT NULL
);

CREATE TABLE AULA (
    IdAula              INT         PRIMARY KEY,
    IdEdificio          INT         NOT NULL,
    IdTipoAula          INT         NOT NULL,
    IdTipoSillas        INT         NOT NULL,
    Capacidad           INT         NOT NULL,
    Nombre              VARCHAR(50),

    FOREIGN KEY (IdEdificio)        REFERENCES EDIFICIO(IdEdificio),
    FOREIGN KEY (IdTipoAula)        REFERENCES TIPO_AULA(IdTipoAula),
    FOREIGN KEY (IdTipoSillas)      REFERENCES TIPO_SILLAS(IdTipoSillas)
);

CREATE TABLE SESION (
    IdSesion            INT         PRIMARY KEY,
    IdGrupo             INT         NOT NULL,
    IdAula              INT         NOT NULL,
    IdDia               INT         NOT NULL,
    Hora_inicio         TIME        NOT NULL,
    Hora_fin            TIME        NOT NULL,

    FOREIGN KEY (IdGrupo)           REFERENCES GRUPO(IdGrupo),
    FOREIGN KEY (IdAula)            REFERENCES AULA(IdAula),
    FOREIGN KEY (IdDia)             REFERENCES DIA(IdDia)
);

CREATE TABLE ESTADO_CURSO (
    IdEstadoCurso       INT         PRIMARY KEY,
    Nombre              VARCHAR(50) NOT NULL
);

ALTER TABLE CURSO
ADD IdEstadoCurso INT;

ALTER TABLE CURSO
MODIFY IdEstadoCurso INT NOT NULL;

ALTER TABLE CURSO
ADD CONSTRAINT fk_curso_estado
FOREIGN KEY (IdEstadoCurso) REFERENCES ESTADO_CURSO(IdEstadoCurso);
